package uz.avaz.instagramclone.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.avaz.instagramclone.entity.template.AbsEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attachment_contents")
public class AttachmentContent extends AbsEntity {

    byte[] content;
}
