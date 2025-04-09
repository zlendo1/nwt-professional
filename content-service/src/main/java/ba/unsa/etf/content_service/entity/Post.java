package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post") // Example: One Post to Many Files
    private List<File> files; // Corrected to List<File>

    @Column(nullable = false)
    private LocalDate postDate;

    @Column(nullable = false)
    private String text;

    @Column(columnDefinition = "BLOB")
    private byte[] imageVideo;

    @Column(nullable=false)
    private String status;

    // Constructors
    public Post() {
    }

    // Getters and Setters

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public byte[] getImageVideo() {
        return imageVideo;
    }

    public void setImageVideo(byte[] imageVideo) {
        this.imageVideo = imageVideo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
