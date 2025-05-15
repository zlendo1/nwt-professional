package ba.unsa.etf.content_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AnalyticsDto {
  private Long analyticsId;

  @NotNull(message = "Post ID is required")
  private Long postId;

  @NotNull(message = "Views count is required")
  @Min(value = 0, message = "Views count cannot be negative")
  private Integer viewsCount;

  @NotNull(message = "Likes count is required")
  @Min(value = 0, message = "Likes count cannot be negative")
  private Integer likesCount;

  @NotNull(message = "Comments count is required")
  @Min(value = 0, message = "Comments count cannot be negative")
  private Integer commentsCount;

  // Getteri i setteri
  public Long getAnalyticsId() {
    return analyticsId;
  }

  public void setAnalyticsId(Long analyticsId) {
    this.analyticsId = analyticsId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public Integer getViewsCount() {
    return viewsCount;
  }

  public void setViewsCount(Integer viewsCount) {
    this.viewsCount = viewsCount;
  }

  public Integer getLikesCount() {
    return likesCount;
  }

  public void setLikesCount(Integer likesCount) {
    this.likesCount = likesCount;
  }

  public Integer getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(Integer commentsCount) {
    this.commentsCount = commentsCount;
  }
}
