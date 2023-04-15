package uz.avaz.instagramclone.service.comment;


import org.springframework.http.HttpEntity;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.projection.post.CommentPostProjection;

public interface CommentService {

    HttpEntity<?> getCommentsPostId(Long postId);

    HttpEntity<?> addCommentToPost(Long postId,String commentBody);

    HttpEntity<?> deleteComment(Long id);

    ApiResponse<CommentPostProjection> getPostOuterComments(Long postId);
}
