package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file")
public class File {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long fileId;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String filePath;

  @Column(nullable = false, length = 50)
  private String fileType;

  @ManyToOne
  @JoinColumn(name = "post_id") // Optional: Specify the foreign key column
  private Post post; // This property name must match the mappedBy value

  // Constructor
  public File() {}

  // Getters and Setters

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }
}
