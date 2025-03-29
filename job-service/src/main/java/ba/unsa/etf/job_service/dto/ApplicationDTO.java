package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String jobTitle;

    @NotBlank(message = "User name is required")
    @Size(max = 100, message = "User name cannot exceed 100 characters")
    private String userName;

    @NotNull(message = "Application date is required")
    private Date applicationDate;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;
}