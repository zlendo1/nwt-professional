package ba.unsa.etf.communication_service.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for creating a new message")
public class CreateMessageDTO {
  @NotNull(message = "User id canot be null")
  @Schema(description = "ID of the user sending the message", example = "1")
  private Long userId;

  @NotNull(message = "Conversation id cannot be null")
  @Schema(description = "ID of the conversation where the message will be sent", example = "1")
  private Long conversationId;

  @NotBlank(message = "Content cannot be blank")
  @Schema(description = "Content/text of the message", example = "Hello, how are you?")
  private String content;
}
