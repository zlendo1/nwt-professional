package ba.unsa.etf.communication_service.dto.conversation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
  @NotNull(message = "Id cannot be null")
  private Long id;

  @NotBlank(message = "Conversation name cannot be blank")
  @Size(max = 50, message = "Conversation name must not exceed 50 characters")
  private String name;
}
