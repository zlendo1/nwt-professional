package ba.unsa.etf.test_skills_service.dto.question;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// DTO for Request (receiving data FROM client to UPDATE Question)
public record UpdateQuestionRequest(
    @NotBlank(message = "Question text cannot be blank") String questionText,
    Integer questionOrder,
    @NotNull(message = "Points cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Points must be non-negative")
        BigDecimal points) {}
