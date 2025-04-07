package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import ba.unsa.etf.job_service.model.enums.JobLocation;
import jakarta.validation.constraints.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

  @NotBlank(message = "Job title is required")
  @Size(max = 100, message = "Job title cannot exceed 100 characters")
  private String jobTitle;

  @NotBlank(message = "Company name is required")
  @Size(max = 100, message = "Company name cannot exceed 100 characters")
  private String companyName;

  @NotBlank(message = "Location is required")
  @Size(max = 100, message = "Location cannot exceed 100 characters")
  private String location;

  @NotNull(message = "Location type is required")
  private JobLocation locationType;

  @NotNull(message = "Employment type is required")
  private EmploymentType employmentType;

  @PastOrPresent(message = "Publish date must be in the past or present")
  private Date publishDate;

  // Novi atribut za expiration date
  @Future(message = "Expiration date must be in future")
  private Date expirationDate;
}
