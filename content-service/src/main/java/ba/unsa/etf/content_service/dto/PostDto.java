package ba.unsa.etf.content_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import ba.unsa.etf.content_service.dto.UserDto;


public class PostDto {

  private Long postId;
  // private Long userId; // UKLONI OVO
  private UserDto user;    // DODAJ OVO - sadržavat će podatke o korisniku

  @NotNull(message = "Post date is required")
  private LocalDate postDate;

  @NotBlank(message = "Post text cannot be blank")
  @Size(min = 2, max = 500, message = "Post text must be between 2 and 500 characters")
  private String text;

  @NotBlank(message = "Status cannot be blank")
  private String status;

  // Možda i imageUrl ako postovi imaju slike
  // private String imageUrl;

  // Getteri i setteri za SVA polja, uključujući i novi 'user' getter/setter
  public Long getPostId() { return postId; }
  public void setPostId(Long postId) { this.postId = postId; }

  public UserDto getUser() { return user; } // Novi getter
  public void setUser(UserDto user) { this.user = user; } // Novi setter

  public LocalDate getPostDate() { return postDate; }
  public void setPostDate(LocalDate postDate) { this.postDate = postDate; }

  public String getText() { return text; }
  public void setText(String text) { this.text = text; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  // public String getImageUrl() { return imageUrl; }
  // public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}