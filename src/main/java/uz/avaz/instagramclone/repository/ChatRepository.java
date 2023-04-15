package uz.avaz.instagramclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.Chat;
import uz.avaz.instagramclone.entity.User;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByUser1OrUser2(User u1, User u2);

    boolean existsByUser1IdAndUser2Id(Long user1_id, Long user2_id);

    Chat findByUser1AndUser2(User u1, User u2);

    Chat findByUser1IdAndUser2Id(Long u1Id, Long u2Id);
}
