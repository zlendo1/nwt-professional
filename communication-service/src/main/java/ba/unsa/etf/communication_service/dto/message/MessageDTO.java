package ba.unsa.etf.communication_service.dto.message;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email;

  @NotNull(message = "Conversation id cannot be null")
  private Long conversationId;

  @NotNull(message = "Content cannot be null")
  private String content;

  @NotNull(message = "created_at cannot be null")
  @PastOrPresent(message = "created_at cannot be in the future")
  private LocalDateTime createdAt;
}
