package ba.unsa.etf.communication_service.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for updating an existing message")
public class UpdateMessageDTO {
  @NotBlank(message = "Content cannot be blank")
  @Schema(
      description = "Updated content/text of the message",
      example = "Hello, how are you doing?")
  private String content;
}
