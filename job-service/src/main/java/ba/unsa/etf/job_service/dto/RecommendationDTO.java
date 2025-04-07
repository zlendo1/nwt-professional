package ba.unsa.etf.job_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {
  @NotBlank(message = "Job title is required")
  @Size(max = 100, message = "Job title cannot exceed 100 characters")
  private String jobTitle;

  @NotBlank(message = "User name is required")
  @Size(max = 100, message = "User name cannot exceed 100 characters")
  private String userName;
}
