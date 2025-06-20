package ba.unsa.etf.communication_service.dto.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Conversation data transfer object")
public class ConversationDTO {
  @NotNull(message = "Id cannot be null")
  @Schema(description = "Unique identifier of the conversation", example = "1")
  private Long id;

  @NotNull(message = "User1_id cannot be null")
  @Schema(description = "ID of the first user in the conversation", example = "1")
  private Long user1_id;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  @Schema(description = "Email address of the first user", example = "user1@example.com")
  private String email1;

  @NotNull(message = "User2_id cannot be null")
  @Schema(description = "ID of the second user in the conversation", example = "2")
  private Long user2_id;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  @Schema(description = "Email address of the second user", example = "user2@example.com")
  private String email2;
}
