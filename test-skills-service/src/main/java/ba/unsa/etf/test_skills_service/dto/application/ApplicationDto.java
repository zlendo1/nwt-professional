package ba.unsa.etf.test_skills_service.dto.application;

import ba.unsa.etf.test_skills_service.dto.test.TestSummaryDto;
import ba.unsa.etf.test_skills_service.dto.user.UserSummaryDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// DTO for Response (sending Application data TO client)
// Includes summary info for related User and Test
public record ApplicationDto(
    Long applicationId,
    UserSummaryDto user, // Embed user summary
    TestSummaryDto test, // Embed test summary
    Long jobPostingId,
    LocalDate applicationDate,
    String status,
    BigDecimal testScore,
    String testStatus,
    LocalDateTime testStartedAt,
    LocalDateTime testCompletedAt,
    LocalDateTime createdAt) {}
