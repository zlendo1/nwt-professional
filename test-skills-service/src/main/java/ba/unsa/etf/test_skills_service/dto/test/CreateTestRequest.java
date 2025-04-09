package ba.unsa.etf.test_skills_service.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// DTO for Request (receiving data FROM client to CREATE Test)
public record CreateTestRequest(
    @NotBlank(message = "Test title cannot be blank") @Size(max = 255) String title,
    String description,
    @PositiveOrZero(message = "Duration must be zero or positive") Integer durationMinutes

    // Optional: List<CreateQuestionRequest> questions // If creating questions along with test
    ) {}
