package ba.unsa.etf.communication_service.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageDTO {
  @NotBlank(message = "Content cannot be blank")
  private String content;
}
