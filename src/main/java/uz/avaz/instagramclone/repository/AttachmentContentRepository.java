package uz.avaz.instagramclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.avaz.instagramclone.entity.Attachment;
import uz.avaz.instagramclone.entity.AttachmentContent;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {

    @Query(
            nativeQuery = true,
            value = "select "
    )
    AttachmentContent findAttachmentContentByAttachment(Attachment attachment);
}
