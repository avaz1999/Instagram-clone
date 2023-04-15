package uz.avaz.instagramclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.avaz.instagramclone.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {



    long countAllByPostId(Long postId);
}
