package uz.avaz.instagramclone.projection.comment;

import org.springframework.beans.factory.annotation.Value;

public interface OuterCommentProjection {

    Long getCommentId();

    String getCommentBody();

    Long getUserPhotoId();

    String getUsername();

    String getLeftDate();

//    @Value("#{@commentLikeRepository.countAllByCommentId(target.commentId)}")
//    Long getLikesCount();

    @Value("#{@commentRepository.countAllByReplyCommentId(target.commentId)}")
    long getReplyCommentsCount();
}
