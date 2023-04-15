package uz.avaz.instagramclone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.avaz.instagramclone.entity.Post;
import uz.avaz.instagramclone.projection.post.CommentPostProjection;
import uz.avaz.instagramclone.projection.post.PostProjection;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            nativeQuery = true,
            value = "select p.id as postId,\n" +
                    "       u.username as username,\n" +
                    "       p.description as description,\n" +
                    "       u.attachment_id as userPhotoId,\n" +
                    "       a.id as postPhotoId\n" +
                    "from posts p\n" +
                    "         join attachments a on p.attachment_id = a.id\n" +
                    "         join users u on p.user_id = u.id\n" +
                    "where p.id = :postId"
    )
    PostProjection getPostById(Long postId);

    @Query(
            nativeQuery = true,
            value = "select p.id                                         as postId,\n" +
                    "       to_char(p.created_at, 'MON-DD-YYYY HH24:MI') as postedDate,\n" +
                    "       u.username                                   as username,\n" +
                    "       p.description                                as description,\n" +
                    "       u.attachment_id                              as userPhotoId,\n" +
                    "       a.id                                         as postPhotoId\n" +
                    "from posts p\n" +
                    "         join attachments a on p.attachment_id = a.id\n" +
                    "         join users u on p.user_id = u.id\n" +
                    "order by p.created_at desc"
    )
    Page<PostProjection> getRecentPosts(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select p.id as postId, " +
                    "       u.attachment_id as userPhotoId,\n" +
                    "       u.username      as username,\n" +
                    "       p.description   as postDescription,\n" +
                    "       to_char(p.created_at, 'Mon/dd/yyyy HH12:MI') as postedDate \n" +
                    "from posts p\n" +
                    "         join users u on p.user_id = u.id\n" +
                    "where p.id = :postId"
    )
    CommentPostProjection getCommentPostProjectionByPostId(Long postId);

    long countAllByUserId(Long userId);
}
