package ba.unsa.etf.test_skills_service.dto.user;

// Simpler DTO for embedding User info in other DTOs (e.g., ApplicationDto)
public record UserSummaryDto(Long userId, String username, String firstName, String lastName) {}
