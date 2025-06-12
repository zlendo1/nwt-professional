package ba.unsa.etf.test_skills_service.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobDTO {

  @NotBlank(message = "Job title is required")
  @Size(max = 100, message = "Job title cannot exceed 100 characters")
  private String jobTitle;
}
