package ba.unsa.etf.communication_service.dto.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for creating a new conversation")
public class CreateConversationDTO {
  @NotNull(message = "User1_id cannot be null")
  @Schema(description = "ID of the first user to include in the conversation", example = "1")
  private Long user1_id;

  @NotNull(message = "User2_id cannot be null")
  @Schema(description = "ID of the second user to include in the conversation", example = "2")
  private Long user2_id;
}
