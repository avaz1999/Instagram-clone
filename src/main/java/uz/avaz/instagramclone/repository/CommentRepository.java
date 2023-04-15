package uz.avaz.instagramclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.Comment;
import uz.avaz.instagramclone.projection.CommentProjection;
import uz.avaz.instagramclone.projection.comment.InnerCommentProjection;
import uz.avaz.instagramclone.projection.comment.OuterCommentProjection;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            nativeQuery = true,
            value = "select c.id as commentId, \n" +
                    "       c.text as commentBody,\n" +
                    "       u.attachment_id as userPhotoId,\n" +
                    "       u.username as username,\n" +
                    "       to_char(c.created_at, 'Mon/dd/yyyy HH12:MI') as leftDate\n" +
                    "from comments c \n" +
                    "join users u on c.user_id = u.id\n" +
                    "join posts p on c.post_id = p.id\n" +
                    "where p.id = :postId and c.reply_comment_id is null "
    )
    List<OuterCommentProjection> getAllOuterCommentsByPostId(Long postId);

    @Query(nativeQuery = true,
            value = "select c.id as id,\n" +
                    "       c.text as text,\n" +
                    "       c.user_id as userId,\n" +
                    "       u.username as username,\n" +
                    "       u.created_at as createdAt,\n" +
                    "       rc.id as toReplyCommentId,\n" +
                    "       rc.text as toReplyCommentText,\n" +
                    "       rc.user_id as toReplyCommentUserId,\n" +
                    "       u2.username as toReplyCommentUsername\n" +
                    "from comments c\n" +
                    "         join users u on c.user_id = u.id\n" +
                    "         left join comments rc on rc.id = c.reply_comment_id\n" +
                    "    left join  users u2 on rc.user_id=u2.id where c.post_id =:postId")
    List<CommentProjection> getCommentsPostId(Long postId);

    long countAllByReplyCommentId(Long replyCommentId);

    @Query(
            nativeQuery = true,
            value = "select c.id                                         as commentId,\n" +
                    "       c.text                                       as commentBody,\n" +
                    "       u.attachment_id                              as userPhotoId,\n" +
                    "       u.username                                   as username,\n" +
                    "       to_char(c.created_at, 'Mon/dd/yyyy HH12:MI') as leftDate,\n" +
                    "       c2.id                                        as replyToCommentId,\n" +
                    "       u2.username                                  as replyCommentUsername\n" +
                    "from comments c\n" +
                    "         join users u on c.user_id = u.id\n" +
                    "         join comments c2 on c2.id = c.reply_comment_id\n" +
                    "         join users u2 on u2.id = c2.user_id\n" +
                    "where c.reply_comment_id = :parentCommentId\n"
    )
    List<InnerCommentProjection> getParentCommentInnerComments(Long parentCommentId);
}
