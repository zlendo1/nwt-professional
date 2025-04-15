package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import ba.unsa.etf.job_service.model.enums.JobLocation;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String jobUUID = UUID.randomUUID().toString();

  // Explicitly set the fetching strategy to Lazy (default) for company
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  private String title;
  private String description;
  private String location;

  @Enumerated(EnumType.STRING)
  private JobLocation locationType;

  @Enumerated(EnumType.STRING)
  private EmploymentType employmentType;

  @Column(columnDefinition = "DATETIME")
  private Date publishDate;

  @Column(columnDefinition = "DATETIME")
  private Date expirationDate;

  private Integer applicationCount;
}
