package uz.avaz.instagramclone.projection.comment;

public interface InnerCommentProjection extends OuterCommentProjection {

    Long getReplyToCommentId();

    String getReplyCommentUsername();
}
