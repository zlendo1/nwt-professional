package ba.unsa.etf.content_service.dto;

import java.time.LocalDate;

public class PostDto {

    private Long postId;
    private Long userId; // Ovdje ostaje "userId", ali povezujemo s `user.id` iz entiteta
    private LocalDate postDate;
    private String text;
    private String status;

    // imageVideo izostavljen jer je BLOB - kasnije provjeriti

    // Getteri i setteri
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

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
