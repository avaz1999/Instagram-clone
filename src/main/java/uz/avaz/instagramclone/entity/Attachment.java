package uz.avaz.instagramclone.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;
import uz.avaz.instagramclone.entity.template.AbsEntity;

import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attachments")
public class Attachment extends AbsEntity {

    long size;
    String contentType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    AttachmentContent attachmentContent;

    public static Attachment prepareAttachment(MultipartFile file) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(file.getOriginalFilename());
        if (!file.isEmpty()) {
            String[] split = file.getOriginalFilename().split("\\.");
            if (2 > split.length) {
                return null;
            }
            return new Attachment(
                    file.getSize(),
                    file.getContentType(),
                    new AttachmentContent(file.getBytes()));
        }
        return null;
    }
}
