package ba.unsa.etf.test_skills_service.dto.application;

import jakarta.validation.constraints.NotNull;

// DTO for Request (receiving data FROM client to CREATE Application)
public record CreateApplicationRequest(
    @NotNull(message = "User ID cannot be null") Long userId,
    @NotNull(message = "Test ID cannot be null") Long testId,
    Long jobPostingId // Optional
    ) {}
