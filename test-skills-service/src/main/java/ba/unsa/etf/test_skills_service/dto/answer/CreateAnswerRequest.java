package ba.unsa.etf.test_skills_service.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO for Request (receiving data FROM client to CREATE Answer)
// questionId usually from path parameter (e.g., POST /api/questions/{questionId}/answers)
public record CreateAnswerRequest(
    @NotBlank(message = "Answer text cannot be blank") String answerText,
    @NotNull(message = "'isCorrect' flag must be provided") Boolean isCorrect) {}
