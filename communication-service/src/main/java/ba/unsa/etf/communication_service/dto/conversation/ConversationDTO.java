package ba.unsa.etf.communication_service.dto.conversation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
  @NotNull(message = "Id cannot be null")
  private Long id;

  @NotNull(message = "User1_id cannot be null")
  private Long user1_id;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email1;

  @NotNull(message = "User2_id cannot be null")
  private Long user2_id;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email2;
}
