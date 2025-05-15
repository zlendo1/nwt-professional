package ba.unsa.etf.content_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PostDto {

  private Long postId;

  @NotNull(message = "User ID is required")
  private Long userId; // Ovdje ostaje "userId", ali povezujemo s `user.id` iz entiteta

  @NotNull(message = "Post date is required")
  private LocalDate postDate;

  @NotBlank(message = "Post text cannot be blank")
  @Size(min = 2, max = 500, message = "Post text must be between 2 and 500 characters")
  private String text;

  @NotBlank(message = "Status cannot be blank")
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
