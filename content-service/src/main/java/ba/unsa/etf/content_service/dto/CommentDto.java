package ba.unsa.etf.content_service.dto;

import java.time.LocalDate;

public class CommentDto {
    private Long commentId;
    private Long postId; // ID posta kojem pripada komentar
    private Long userId; // ID korisnika koji je napisao komentar
    private String text;
    private LocalDate postdate;

    // Getteri i setteri
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPostdate() {
        return postdate;
    }

    public void setPostdate(LocalDate postdate) {
        this.postdate = postdate;
    }
}

