package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "saved_searches")
public class SavedSearch {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String keywords;
  private String location;

  @Enumerated(EnumType.STRING)
  private EmploymentType employmentType;

  @Column(columnDefinition = "DATETIME")
  private Date saveDate;
}
