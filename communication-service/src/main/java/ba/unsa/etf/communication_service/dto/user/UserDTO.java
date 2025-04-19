package ba.unsa.etf.communication_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @NotNull(message = "Id cannot be null")
  private Long id;

  @NotNull(message = "Username cannot be null")
  @Size(max = 50, message = "Username cannot be longer than 50 characters")
  private String username;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email;
}
