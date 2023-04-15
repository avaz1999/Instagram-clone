package uz.avaz.instagramclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.avaz.instagramclone.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
