package uz.avaz.instagramclone.projection.post;

import org.springframework.beans.factory.annotation.Value;

public interface PostProjection {

    Long getPostId();

    Long getPostPhotoId();

    String getDescription();

    String getPostedDate();

    String getUsername();

    Long getUserPhotoId();

    @Value(value = "#{@likeRepository.countAllByPostId(target.postId)}")
    long likesCount();
}
