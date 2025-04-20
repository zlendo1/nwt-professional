package ba.unsa.etf.communication_service.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
  @NotNull(message = "Id cannot be null")
  private Long id;

  @NotNull(message = "User id cannot be null")
  private Long userId;

  @NotBlank(message = "Username cannot be blank")
  @Size(max = 50, message = "Username cannot be longer than 50 characters")
  private String username;

  @NotNull(message = "Conversation id cannot be null")
  private Long conversationId;

  @NotNull(message = "Content cannot be null")
  private String content;

  @NotNull(message = "created_at cannot be null")
  @PastOrPresent(message = "created_at cannot be in the future")
  private LocalDateTime createdAt;
}
