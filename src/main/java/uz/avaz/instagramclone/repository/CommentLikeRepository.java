package uz.avaz.instagramclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.CommentLike;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    long countAllByCommentId(Long commentId);
}
