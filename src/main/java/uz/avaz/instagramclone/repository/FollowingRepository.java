package uz.avaz.instagramclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.Following;
import uz.avaz.instagramclone.projection.account.SubscriptionRequestAccountProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    boolean existsByFromUserIdAndToUserId(Long fromUser_id, Long toUser_id);

    @Query(
            nativeQuery = true,
            value = "select count(*)\n" +
                    "from followings f\n" +
                    "         join users u on u.id = f.to_user_id\n" +
                    "where f.to_user_id = :userId\n" +
                    "  and f.state = true;"
    )
    long getFollowersCount(Long userId);

    @Query(
            nativeQuery = true,
            value = "select count(*)\n" +
                    "from followings f\n" +
                    "where f.state = true\n" +
                    "  and f.from_user_id = :userId"
    )
    long getFollowingsCount(Long userId);

    @Query(
            nativeQuery = true,
            value = "select u2.id            as accountId, " +
                    "f.id as subscriptionRequestId, \n" +
                    "       u2.attachment_id as accountPhotoId,\n" +
                    "       u2.username      as username\n" +
                    "from followings f\n" +
                    "         join users u on u.id = f.to_user_id\n" +
                    "         join users u2 on u2.id = f.from_user_id\n" +
                    "where f.to_user_id = :accountId\n" +
                    "  and f.state = false"
    )
    List<SubscriptionRequestAccountProjection> getAllRequestedAccounts(Long accountId);

    boolean existsByFromUserIdAndToUserIdAndState(Long fromUserId, Long toUserId, boolean state);

    boolean existsByIdAndToUserIdAndState(Long id, Long toUser_id, boolean state);

    Optional<Following> findByIdAndToUserIdAndState(Long id, Long toUser_id, boolean state);

    Optional<Following> findByFromUserIdAndToUserId(Long fromUser_id, Long toUser_id);
}
