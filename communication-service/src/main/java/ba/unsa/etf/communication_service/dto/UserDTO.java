package ba.unsa.etf.communication_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @NotBlank(message = "UUID cannot be blank")
  private String uuid;

  @NotNull(message = "Name cannot be null")
  @Size(max = 50, message = "Name cannot be longer than 50 characters")
  private String name;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email must have proper format")
  private String email;
}
