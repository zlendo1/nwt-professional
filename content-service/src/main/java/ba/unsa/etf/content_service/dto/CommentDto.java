package ba.unsa.etf.content_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CommentDto {
  private Long commentId;

  @NotNull(message = "Post ID is required")
  private Long postId;

  @NotNull(message = "User ID is required")
  private Long userId;

  @NotBlank(message = "Comment text cannot be blank")
  @Size(min = 2, max = 255, message = "Comment must be between 2 and 255 characters")
  private String text;

  @NotNull(message = "Post date is required")
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
