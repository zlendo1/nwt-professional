package ba.unsa.etf.test_skills_service.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO for Request (receiving data FROM client to UPDATE Answer)
public record UpdateAnswerRequest(
    @NotBlank(message = "Answer text cannot be blank") String answerText,
    @NotNull(message = "'isCorrect' flag must be provided") Boolean isCorrect) {}
