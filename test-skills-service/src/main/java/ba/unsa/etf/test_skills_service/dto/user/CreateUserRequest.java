package ba.unsa.etf.test_skills_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for Request (receiving data FROM client to CREATE User)
public record CreateUserRequest(
    @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,
    @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid")
        String email,
    @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password, // Receive plain password, service will hash it
    String firstName,
    String lastName) {}
