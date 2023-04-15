package uz.avaz.instagramclone.projection.post;

import org.springframework.beans.factory.annotation.Value;
import uz.avaz.instagramclone.projection.comment.OuterCommentProjection;

import java.util.List;

public interface CommentPostProjection {

    Long getPostId();

    Long getUserPhotoId();

    String getUsername();

    String getPostDescription();

    String getPostedDate();

    @Value(value = "#{@commentRepository.getAllOuterCommentsByPostId(target.postId)}")
    List<OuterCommentProjection> getOuterComments();
}
