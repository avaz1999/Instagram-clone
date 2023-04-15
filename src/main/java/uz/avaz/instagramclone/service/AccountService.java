package uz.avaz.instagramclone.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.avaz.instagramclone.entity.Following;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.projection.account.AccountProjection;
import uz.avaz.instagramclone.projection.account.SubscriptionRequestAccountProjection;
import uz.avaz.instagramclone.repository.FollowingRepository;
import uz.avaz.instagramclone.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    public ApiResponse<AccountProjection> viewProfile(Long profileId) {
        if (!userRepository.existsById(profileId)) {
            return new ApiResponse<>(false, "Profile not found!");
        }
        AccountProjection userProfile = userRepository.getUserProfile(profileId);
        return new ApiResponse<>(true, null, userProfile);
    }

    /**
     * AUTHENTICATED USER ATTEMPTS TO FOLLOW THE SPECIFIED ACCOUNT
     *
     * @param followedAccountId - THE USER WHO THE AUTHENTICATED USER ATTEMPTS TO FOLLOW TO
     * @param userDetails       - THE AUTHENTICATED USER ATTEMPTING TO FOLLOW
     */
    public ApiResponse<?> follow(Long followedAccountId, UserDetails userDetails) {
        User followedAccount = userRepository.findById(followedAccountId).orElse(null);
        User follower = userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        if (followedAccount == null || follower == null) {
            return new ApiResponse<>(false, "Follower or followed account not found!");
        }
        if (followedAccount.getId().equals(follower.getId())) {
            return new ApiResponse<>(false, "Follower and followed accounts cannot be the same!");
        }

        if (!followingRepository.existsByFromUserIdAndToUserId(follower.getId(), followedAccount.getId())) {
            Following following = new Following(follower, followedAccount);
            if (followedAccount.isPublic()) {
                following.setState(true);
                followingRepository.save(following);
                return new ApiResponse<>(true, "Following!");
            } else {
                following.setState(false);
                followingRepository.save(following);
                return new ApiResponse<>(true, "Requested!");
            }
        }
        return new ApiResponse<>(false, "Following!");
    }

    /**
     * AUTHENTICATED USER TRIES TO UNFOLLOW FROM THE SPECIFIED USER
     *
     * @param unfollowedAccountId - THE USER WHO THE AUTHENTICATED USER TRIES TO UNFOLLOW FROM
     * @param userDetails         - THE AUTHENTICATED TRYING TO ATTEMPT TO UNFOLLOW FROM THE SPECIFIED USER
     */
    public ApiResponse<?> unfollow(Long unfollowedAccountId, UserDetails userDetails) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        if (!userRepository.existsById(unfollowedAccountId)) {
            return new ApiResponse<>(false, "Account not found!");
        }

        Following following = followingRepository.findByFromUserIdAndToUserId(user.getId(), unfollowedAccountId).orElse(null);
        if (following != null) {
            followingRepository.delete(following);
            return new ApiResponse<>(true, "Unfollowed!");
        }
        return new ApiResponse<>(true, "Account not found!");
    }

    public ApiResponse<?> getAccountAllRequestedAccounts(UserDetails userDetails) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        List<SubscriptionRequestAccountProjection> requestedAccounts = followingRepository.getAllRequestedAccounts(user.getId());
        return new ApiResponse<>(true, null, requestedAccounts);
    }

    public ApiResponse<?> acceptSubscriptionRequest(Long subscriptionRequestId, UserDetails principal) {
        User user = userRepository
                .findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + principal.getUsername() + ", not found!"));

        Following following = followingRepository.findByIdAndToUserIdAndState(subscriptionRequestId, user.getId(), false).orElse(null);
        if (following != null) {
            following.setState(true);
            followingRepository.save(following);
            return new ApiResponse<>(true, "Accepted!");
        }
        return new ApiResponse<>(false, "Subscription request not found!");
    }

    public ApiResponse<?> rejectSubscriptionRequest(Long subscriptionRequestId, UserDetails userDetails) {
        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + userDetails.getUsername() + ", not found!"));

        Following subscriptionRequest = followingRepository
                .findByIdAndToUserIdAndState(subscriptionRequestId, user.getId(), false)
                .orElse(null);

        if (subscriptionRequest == null) {
            return new ApiResponse<>(false, "Subscription request not found!");
        }

        followingRepository.delete(subscriptionRequest);
        return new ApiResponse<>(true, "Subscription request has been rejected!");
    }
}
