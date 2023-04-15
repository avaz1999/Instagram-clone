package uz.avaz.instagramclone.service;

import uz.avaz.instagramclone.dto.PostDto;
import uz.avaz.instagramclone.entity.Attachment;
import uz.avaz.instagramclone.entity.Post;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.projection.post.PostProjection;
import uz.avaz.instagramclone.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PostService {
    PostRepository postRepository;

    UserRepository userRepository;

    AttachmentRepository attachmentRepository;

    AttachmentContentRepository attachmentContentRepository;

    LikeRepository likeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.likeRepository = likeRepository;
    }

    public Post addPost(PostDto postDto, UserDetails principal) {
        // TODO
        User user = userRepository
                .findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + principal.getUsername() + ", not found!"));

        Post post = new Post(user, postDto.getDescription());
        try {
            Attachment attachment = Attachment.prepareAttachment(postDto.getImageFile());
            if (attachment != null) {
                post.setAttachment(attachment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        }
    }

    public Page<PostProjection> getRecentPosts(Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);
        return postRepository.getRecentPosts(of);
    }

    public PostProjection getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }
}
