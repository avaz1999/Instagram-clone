package uz.avaz.instagramclone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.avaz.instagramclone.entity.template.AbsEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "chats")
public class Chat extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    User user2;

    @OneToMany(mappedBy = "chat")
    List<Message> messages;

    public Chat(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
