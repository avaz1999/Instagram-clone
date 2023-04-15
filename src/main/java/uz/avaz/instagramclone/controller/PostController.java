package uz.avaz.instagramclone.controller;

import uz.avaz.instagramclone.dto.PostDto;
import uz.avaz.instagramclone.service.PostService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Done
    @GetMapping("/{postId}")
    public HttpEntity<?> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    //Done
    @GetMapping
    public HttpEntity<?> getRecentPosts(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(postService.getRecentPosts(page, size));
    }

    //Done
    @PostMapping
    public HttpEntity<?> addPost(@ModelAttribute PostDto postDto, @AuthenticationPrincipal UserDetails principal) throws IOException {
        return ResponseEntity.ok(postService.addPost(postDto, principal));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
