package ba.unsa.etf.job_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyDTO {

  @NotNull(message = "Company UUID cannot be null")
  @Size(min = 1, message = "Company UUID cannot be empty")
  private String companyUUID;

  @NotBlank(message = "Company name cannot be blank")
  @Size(max = 100, message = "Company name cannot exceed 100 characters")
  private String name;
}
