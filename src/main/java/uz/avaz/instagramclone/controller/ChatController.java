package uz.avaz.instagramclone.controller;

import uz.avaz.instagramclone.entity.Chat;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.payload.MessageDto;
import uz.avaz.instagramclone.repository.UserRepository;
import uz.avaz.instagramclone.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserRepository userRepository;

    @Autowired
    public ChatController(ChatService chatService, UserRepository userRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create/{guestId}")
    public ResponseEntity<?> createChat(@PathVariable Long guestId, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<Chat> response = chatService.createChat(userDetails, guestId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getUserChats(@AuthenticationPrincipal UserDetails userDetails) {
        List<Chat> chats = chatService.getUserChats(userDetails);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<?> findChatMessages(@PathVariable Long chatId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        List<MessageDto> chatMessages = chatService.findChatMessages(chatId);
        chatService.makeChatMessagesRead(chatId, user.getId());
        return ResponseEntity.ok(chatMessages);
    }

    @PostMapping("/{chatId}/sendMessage")
    public ResponseEntity<?> sendMessage(@PathVariable Long chatId,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam(name = "messageBody") String messageBody) {
        boolean sent = chatService.sendMessage(userDetails, chatId, messageBody);
        return ResponseEntity.status(sent ? 200 : 409).body(null);
    }

    @DeleteMapping("/{messageId}/delete")
    public ResponseEntity<Long> deleteMessage(@PathVariable Long messageId) {
        Long deleteMessageId = chatService.deleteMessage(messageId);
        return ResponseEntity.ok(deleteMessageId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserForHost(@RequestParam(name = "searchText") String searchText,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        List<User> foundUsers = chatService.searchUserForHost(userDetails, searchText);
        return ResponseEntity.ok(foundUsers);
    }
}
