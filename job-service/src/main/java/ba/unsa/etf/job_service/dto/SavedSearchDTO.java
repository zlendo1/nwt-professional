package ba.unsa.etf.job_service.dto;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
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
public class SavedSearchDTO {

    @NotBlank(message = "Keywords are required")
    @Size(max = 200, message = "Keywords cannot exceed 200 characters")
    private String keywords;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotNull(message = "Save date is required")
    private Date saveDate;
}
