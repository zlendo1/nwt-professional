package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import ba.unsa.etf.job_service.model.enums.JobLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchDTO {
  private String jobTitle;
  private String companyName;
  private String location;
  private JobLocation locationType;
  private EmploymentType employmentType;
  private String publishDateFrom;
  private String publishDateTo;
}
