package ba.unsa.etf.communication_service.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageDTO {
  @NotNull(message = "User id canot be null")
  private Long userId;

  @NotNull(message = "Conversation id cannot be null")
  private Long conversationId;

  @NotBlank(message = "Content cannot be blank")
  private String content;
}
