package uz.avaz.instagramclone.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.avaz.instagramclone.entity.Chat;
import uz.avaz.instagramclone.entity.User;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    Long id;
    String message;
    String sentAt;
    Boolean isRead;
    User fromUser;
    Chat chat;
}
