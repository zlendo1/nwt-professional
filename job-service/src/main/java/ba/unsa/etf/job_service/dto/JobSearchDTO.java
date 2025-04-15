package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import ba.unsa.etf.job_service.model.enums.JobLocation;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobSearchDTO {
  private String jobTitle;
  private String companyName;
  private String location;
  private JobLocation locationType;
  private EmploymentType employmentType;
  private String publishDateFrom;
  private String publishDateTo;
}
