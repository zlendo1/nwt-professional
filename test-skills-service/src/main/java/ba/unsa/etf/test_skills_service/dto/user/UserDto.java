package ba.unsa.etf.test_skills_service.dto.user;

import java.time.LocalDateTime;

// DTO for Response (sending User data TO client)
// Excludes sensitive info like passwordHash
public record UserDto(
    Long userId,
    String username,
    String email,
    String firstName,
    String lastName,
    LocalDateTime createdAt) {}
