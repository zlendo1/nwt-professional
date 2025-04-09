package ba.unsa.etf.test_skills_service.dto.application;

import jakarta.validation.constraints.NotBlank;

// Specific DTO for updating only the status (example)
public record UpdateApplicationStatusRequest(
    @NotBlank(message = "Status cannot be blank") String status) {}
