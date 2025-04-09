package ba.unsa.etf.communication_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
  @NotBlank(message = "User name cannot be blank")
  @Size(max = 50, message = "User name cannot exceed 50 characters")
  private String userName;

  @NotBlank(message = "Conversation name cannot be blank")
  @Size(max = 50, message = "Conversation name cannot exceed 50 characters")
  private String conversationName;

  @NotNull(message = "Content cannot be null")
  private String content;

  @NotBlank(message = "Type cannot be blank")
  private String type;

  @NotBlank(message = "Encoding cannot be blank")
  private String encoding;

  @NotNull(message = "created_at cannot be null")
  @PastOrPresent(message = "created_at cannot be in the future")
  private Instant created_at;
}
