package ba.unsa.etf.test_skills_service.dto.application;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// DTO for submitting test results (if score calculated elsewhere)
// As used in the previous controller example (Map<String, BigDecimal>)
// A more robust approach would involve submitting actual answers.
public record CompleteTestRequest(@NotNull(message = "Score cannot be null") BigDecimal score) {}
