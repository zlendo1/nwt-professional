package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationDTO {

  @NotNull(message = "Job ID is required")
  private Long jobId;

  @NotNull(message = "User ID is required")
  private Long userId;

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
