package uz.avaz.instagramclone.service;

import org.modelmapper.ModelMapper;
import uz.avaz.instagramclone.entity.Chat;
import uz.avaz.instagramclone.entity.Message;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.payload.MessageDto;
import uz.avaz.instagramclone.repository.ChatRepository;
import uz.avaz.instagramclone.repository.MessageRepository;
import uz.avaz.instagramclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       UserRepository userRepository,
                       MessageRepository messageRepository,
                       UserService userService, ModelMapper modelMapper) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<Chat> createChat(UserDetails userDetails, Long guestId) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        if (chatRepository.existsByUser1IdAndUser2Id(user.getId(), guestId) || chatRepository.existsByUser1IdAndUser2Id(guestId, user.getId())) {
            Chat chat = chatRepository.findByUser1IdAndUser2Id(user.getId(), guestId);
            if (chat == null) {
                chat = chatRepository.findByUser1IdAndUser2Id(guestId, user.getId());
            }
            return new ApiResponse<>(true, null, chat);
        }


        User host = userRepository.findById(user.getId()).orElse(null);
        User guest = userRepository.findById(guestId).orElse(null);

        if (host == null || guest == null || host.equals(guest))
            return new ApiResponse<>(true, "Host or guest not found!");

        return new ApiResponse<>(true, null, chatRepository.save(new Chat(host, guest)));
    }

    public List<Chat> getUserChats(UserDetails userDetails) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        return chatRepository.findAllByUser1OrUser2(user, user);
    }

    public List<MessageDto> findChatMessages(Long chatId) {
        List<Message> messages = messageRepository.findAllByChatId(chatId);
        List<MessageDto> messageDtoList = new ArrayList<>();
        for (Message message : messages) {
            MessageDto messageDto = modelMapper.map(message, MessageDto.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = message.getSentDate().format(formatter);
            messageDto.setSentAt(formatDateTime);
            messageDtoList.add(messageDto);
        }
        return messageDtoList;
    }

    public boolean sendMessage(UserDetails userDetails, Long chatId, String messageBody) {
        User sender = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        if (messageBody == null || messageBody.isBlank())   //TODO isBlank
            return false;

        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) return false;

        if (!chat.getUser1().equals(sender) && !chat.getUser2().equals(sender)) return false;

        Message message = new Message(messageBody, chat, sender, false, LocalDateTime.now());
        messageRepository.save(message);
        return true;
    }

    //    todo : TEST @Transactional
    public void makeChatMessagesRead(Long chatId, Long userId) {
        if (chatRepository.existsById(chatId)) {
            List<Message> messages = messageRepository.findAllByChatId(chatId);
            for (Message message : messages) {
                if (!message.getIsRead() && !message.getFrom().getId().equals(userId)) {
                    message.setIsRead(true);
                    messageRepository.save(message);
                }
            }
        }
    }

    public List<MessageDto> getNewMessages(List<MessageDto> chatMessages, Long userId) {
        List<MessageDto> messageDtoList = new ArrayList<>();
        for (MessageDto chatMessage : chatMessages) {
            if (!chatMessage.getIsRead() && !chatMessage.getFromUser().getId().equals(userId)) {
                messageDtoList.add(chatMessage);
            }
        }
        return messageDtoList;
    }

    public Long deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).get();
        messageRepository.delete(message);
        return message.getChat().getId();
    }

    public List<User> searchUserForHost(UserDetails userDetails, String searchText) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        return userRepository.searchUserForHost(user.getId(), searchText);
    }
}
