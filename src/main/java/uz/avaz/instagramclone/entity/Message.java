package uz.avaz.instagramclone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import uz.avaz.instagramclone.entity.template.AbsEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "messages")
public class Message extends AbsEntity {

    String message;

    @Column(nullable = false)
    LocalDateTime sentDate;

    @ColumnDefault(value = "false")
    Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    User from;

    @ManyToOne
    @JoinColumn(nullable = false, name = "chat_id")
    Chat chat;

    public Message(String message, Chat chat, User from, Boolean isRead, LocalDateTime sentDate) {
        this.message = message;
        this.sentDate = sentDate;
        this.isRead = isRead;
        this.from = from;
        this.chat = chat;
    }
}
