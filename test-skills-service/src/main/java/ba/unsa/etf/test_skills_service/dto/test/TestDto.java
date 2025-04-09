package ba.unsa.etf.test_skills_service.dto.test;

import java.time.LocalDateTime;

// DTO for Response (sending Test data TO client)
// Can optionally include nested Question DTOs
public record TestDto(
    Long testId, String title, String description, Integer durationMinutes, LocalDateTime createdAt
    // Optional: List<QuestionDto> questions // Requires QuestionDto
    ) {}
