package uz.avaz.instagramclone.controller;

import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.projection.comment.InnerCommentProjection;
import uz.avaz.instagramclone.projection.post.CommentPostProjection;
import uz.avaz.instagramclone.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    final CommentServiceImpl commentService;

    @GetMapping
    public HttpEntity<?> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsPostId(postId);
    }

    @PostMapping("/{postId}")
    public HttpEntity<?> addComment(@PathVariable Long postId, @RequestBody String commentBody) {
        return commentService.addCommentToPost(postId, commentBody);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping("/getPostOuterComments/{postId}")
    public ResponseEntity<ApiResponse<?>> getPostOuterComments(@PathVariable Long postId) {
        ApiResponse<CommentPostProjection> commentPost = commentService.getPostOuterComments(postId);
        return ResponseEntity.ok(commentPost);
    }

    @GetMapping("/{parentCommentId}/innerComments")
    public ResponseEntity<?> getCommentReplyComments(@PathVariable Long parentCommentId) {
        ApiResponse<List<InnerCommentProjection>> response = commentService.getCommentInnerComments(parentCommentId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
