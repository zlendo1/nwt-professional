package ba.unsa.etf.communication_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email;
}
