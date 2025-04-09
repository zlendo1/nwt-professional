package ba.unsa.etf.test_skills_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for Request (receiving data FROM client to UPDATE User)
// Password update might be handled separately for security
public record UpdateUserRequest(
    @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,
    @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid")
        String email,
    String firstName,
    String lastName) {}
