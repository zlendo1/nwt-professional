package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "analytics") // Matches your table name
public class Analytics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long analyticsId;

  @OneToOne // One-to-one relationship with Post
  @JoinColumn(name = "postId", nullable = false) // Foreign key referencing Post table
  private Post post;

  @Column(nullable = false)
  private Integer viewsCount;

  @Column(nullable = false)
  private Integer likesCount;

  @Column(nullable = false)
  private Integer commentsCount;

  // Constructors
  public Analytics() {}

  // Getters and Setters

  public Long getAnalyticsId() {
    return analyticsId;
  }

  public void setAnalyticsId(Long analyticsId) {
    this.analyticsId = analyticsId;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
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
