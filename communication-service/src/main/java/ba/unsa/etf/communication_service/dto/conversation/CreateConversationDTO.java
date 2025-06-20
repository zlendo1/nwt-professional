package ba.unsa.etf.communication_service.dto.conversation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationDTO {
  @NotNull(message = "User1_id cannot be null")
  private Long user1_id;

  @NotNull(message = "User2_id cannot be null")
  private Long user2_id;
}
