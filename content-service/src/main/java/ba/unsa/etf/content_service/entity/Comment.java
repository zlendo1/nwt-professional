package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId; // Matches your CommentID in the database

  @ManyToOne
  @JoinColumn(name = "postId", nullable = false) // Foreign key referencing Post table
  private Post post; // Assuming we have a Post entity

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false) // Foreign key referencing User table
  private User user;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false, name = "postdate")
  private LocalDate postdate;

  // Constructors
  public Comment() {}

  // Getters and Setters

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
