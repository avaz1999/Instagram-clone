package uz.avaz.instagramclone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.avaz.instagramclone.entity.template.AbsEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "comments")
public class Comment extends AbsEntity {

    @Column(name = "text", nullable = false)
    String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "reply_comment_id")
    Comment replyComment;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    Post post;
}
