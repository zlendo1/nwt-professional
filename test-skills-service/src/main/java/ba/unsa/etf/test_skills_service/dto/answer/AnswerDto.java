package ba.unsa.etf.test_skills_service.dto.answer;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

// DTO for Response (sending Answer data TO client)
// Consider if 'isCorrect' should always be exposed, or only after grading/admin view
public record AnswerDto(
    Long answerId,
    Long questionId, // Include parent question ID
    String answerText,
    @NotNull Boolean isCorrect, // Usually needed to show the correct answer after test completion
    LocalDateTime createdAt) {}
