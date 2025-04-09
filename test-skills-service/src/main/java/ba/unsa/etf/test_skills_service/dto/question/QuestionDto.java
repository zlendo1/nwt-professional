package ba.unsa.etf.test_skills_service.dto.question;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// If including Answer DTOs

// DTO for Response (sending Question data TO client)
// Can optionally include nested Answer DTOs
public record QuestionDto(
    Long questionId,
    Long testId, // Include parent test ID
    String questionText,
    Integer questionOrder,
    BigDecimal points,
    LocalDateTime createdAt
    // Optional: List<AnswerDto> answers // Requires AnswerDto
    ) {}
