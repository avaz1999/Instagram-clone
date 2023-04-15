package uz.avaz.instagramclone.projection;


// t.me/superJavaDeveloper 12.04.2022;


public interface CommentProjection {
    Long getId();

    String getText();

    Long getUserId();

    String getUsername();

    String getCreatedAt();

    Long getToReplyCommentId();

    String getToReplyCommentText();

    Long getToReplyCommentUserId();

    String getToReplyCommentUsername();
}

