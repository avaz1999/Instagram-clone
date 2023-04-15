package uz.avaz.instagramclone.controller;

import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.projection.account.AccountProjection;
import uz.avaz.instagramclone.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountProjection>> viewProfile(@PathVariable Long accountId) {
        ApiResponse<AccountProjection> response = accountService.viewProfile(accountId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/{followedAccountId}/follow")
    public ResponseEntity<?> follow(@PathVariable Long followedAccountId, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<?> response = accountService.follow(followedAccountId, userDetails);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/{unfollowedAccountId}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable Long unfollowedAccountId, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<?> response = accountService.unfollow(unfollowedAccountId, userDetails);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response); // todo
    }

    @GetMapping("/subscriptionRequests/mySubscriptionRequests")
    public ResponseEntity<?> getMyFollowRequests(@AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<?> response = accountService.getAccountAllRequestedAccounts(userDetails);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/subscriptionRequests/{subscriptionRequestId}/accept")
    public ResponseEntity<?> acceptSubscriptionRequest(@PathVariable Long subscriptionRequestId, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<?> response = accountService.acceptSubscriptionRequest(subscriptionRequestId, userDetails);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/subscriptionRequests/{subscriptionRequestId}/reject")
    public ResponseEntity<?> rejectSubscriptionRequest(@PathVariable Long subscriptionRequestId, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<?> response = accountService.rejectSubscriptionRequest(subscriptionRequestId, userDetails);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
