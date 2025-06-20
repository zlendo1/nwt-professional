package ba.unsa.etf.communication_service.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Message data transfer object containing message details")
public class MessageDTO {
  @NotNull(message = "Id cannot be null")
  @Schema(description = "Unique identifier of the message", example = "1")
  private Long id;

  @NotNull(message = "User id cannot be null")
  @Schema(description = "ID of the user who sent the message", example = "1")
  private Long userId;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  @Schema(
      description = "Email address of the user who sent the message",
      example = "user@example.com")
  private String email;

  @NotNull(message = "Conversation id cannot be null")
  @Schema(description = "ID of the conversation this message belongs to", example = "1")
  private Long conversationId;

  @NotNull(message = "Content cannot be null")
  @Schema(description = "Content/text of the message", example = "Hello, how are you?")
  private String content;

  @NotNull(message = "created_at cannot be null")
  @PastOrPresent(message = "created_at cannot be in the future")
  @Schema(description = "Timestamp when the message was created", example = "2023-12-01T10:30:00")
  private LocalDateTime createdAt;
}
