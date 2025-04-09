package ba.unsa.etf.test_skills_service.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// DTO for Request (receiving data FROM client to UPDATE Test)
public record UpdateTestRequest(
    @NotBlank(message = "Test title cannot be blank") @Size(max = 255) String title,
    String description,
    @PositiveOrZero(message = "Duration must be zero or positive") Integer durationMinutes) {}
