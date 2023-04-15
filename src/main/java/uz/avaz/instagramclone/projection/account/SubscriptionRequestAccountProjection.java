package uz.avaz.instagramclone.projection.account;

public interface SubscriptionRequestAccountProjection {

    Long getAccountId();

    Long getAccountPhotoId();

    String getUsername();

    Long getSubscriptionRequestId();
}
