package uz.avaz.instagramclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.projection.account.AccountProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "select u.id as profileId,\n" +
                    "       u.username as username,\n" +
                    "       u.attachment_id as userPhotoId,\n" +
                    "       u.full_name as fullName,\n" +
                    "       u.bio as bio\n" +
                    "from users u\n" +
                    "where u.id = :userId"
    )
    AccountProjection getUserProfile(Long userId);

    List<User> findAllByUsernameContainingOrFullNameContainingAndIdNot(String username, String fullName, Long hostId);

    @Query(
            nativeQuery = true,
            value = "select u.*\n" +
                    "from users u\n" +
                    "         join (\n" +
                    "    select case\n" +
                    "               when u.id = :hostId and u2.username like concat('%', :searchText, '%') or\n" +
                    "                    u2.full_name like concat('%', :searchText, '%')\n" +
                    "                   then u2.id\n" +
                    "               when u2.id = :hostId and u.username like concat('%', :searchText, '%') or\n" +
                    "                    u.full_name like concat('%', :searchText, '%')\n" +
                    "                   then u.id end as foundUserId\n" +
                    "    from chats ch\n" +
                    "             join users u on ch.user1_id = u.id\n" +
                    "             join users u2 on u2.id = ch.user1_id\n" +
                    "    where u.id = :hostId\n" +
                    "       or u2.id = :hostId\n" +
                    ") as inn_table on inn_table.foundUserId = u.id"
    )
    List<User> searchUserForHost(Long hostId, String searchText);
}
