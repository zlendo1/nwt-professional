package ba.unsa.etf.communication_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConversationDTO {
  @NotBlank(message = "User name cannot be blank")
  @Size(max = 50, message = "User name cannot exceed 50 characters")
  private String userName;

  @NotBlank(message = "Conversation name cannot be blank")
  @Size(max = 50, message = "Conversation name cannot exceed 50 characters")
  private String conversationName;
}
