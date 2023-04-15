package uz.avaz.instagramclone.service.comment;

import uz.avaz.instagramclone.entity.Comment;
import uz.avaz.instagramclone.entity.Post;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.projection.CommentProjection;
import uz.avaz.instagramclone.projection.comment.InnerCommentProjection;
import uz.avaz.instagramclone.projection.post.CommentPostProjection;
import uz.avaz.instagramclone.repository.CommentRepository;
import uz.avaz.instagramclone.repository.PostRepository;
import uz.avaz.instagramclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    final CommentRepository commentRepository;
    final PostRepository postRepository;
    final UserRepository userRepository;

    @Override
    public HttpEntity<?> getCommentsPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.badRequest().body("Post not found");
        }
        List<CommentProjection> comments = commentRepository.getCommentsPostId(postId);
        return ResponseEntity.ok().body(comments);
    }

    @Override
    public ApiResponse<CommentPostProjection> getPostOuterComments(Long postId) {
        CommentPostProjection commentPost = postRepository.getCommentPostProjectionByPostId(postId);
        return new ApiResponse<>(true, null, commentPost);
    }

    @Override
    public HttpEntity<?> addCommentToPost(Long postId, String commentBody) {
        if (postRepository.findById(postId).isEmpty()) {
            return ResponseEntity.badRequest().body("Post not found");
        }
        Post post = postRepository.findById(postId).get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        if (userRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = userRepository.findByUsername(username).get();


        commentRepository.save(new Comment(commentBody, user, null, post));
        return ResponseEntity.ok().body("Comment successfully added");
    }

    @Override
    public HttpEntity<?> deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Comment not found");
        }
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ApiResponse<List<InnerCommentProjection>> getCommentInnerComments(Long parentCommentId) {
        if (!commentRepository.existsById(parentCommentId)) {
            return new ApiResponse<>(false, "Comment not found!");
        }
        List<InnerCommentProjection> innerComments = commentRepository.getParentCommentInnerComments(parentCommentId);
        return new ApiResponse<List<InnerCommentProjection>>(true, null, innerComments);
    }
}
