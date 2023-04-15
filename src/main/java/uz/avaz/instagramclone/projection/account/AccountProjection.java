package uz.avaz.instagramclone.projection.account;

import org.springframework.beans.factory.annotation.Value;

public interface AccountProjection {

    Long getProfileId();

    String getUsername();

    Long getUserPhotoId();

    @Value(value = "#{@postRepository.countAllByUserId(target.profileId)}")
    long getPostsCount();

    @Value("#{@followingRepository.getFollowersCount(target.profileId)}")
    long getFollowersCount();

    @Value("#{@followingRepository.getFollowingsCount(target.profileId)}")
    long getFollowingsCount();

    String getFullName();

    String getBio();
}
