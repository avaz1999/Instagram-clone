package uz.avaz.instagramclone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.avaz.instagramclone.entity.template.AbsEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "posts")
public class Post extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Attachment attachment;

    String description;

    public Post(User user, String description) {
        this.user = user;
        this.description = description;
    }
}
