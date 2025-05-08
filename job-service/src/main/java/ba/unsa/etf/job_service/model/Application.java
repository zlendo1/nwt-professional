package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String applicationUUID = UUID.randomUUID().toString();

  @ManyToOne
  @JoinColumn(name = "job_id", nullable = false)
  private Job job;

  @ManyToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User user;

  @Column(columnDefinition = "DATETIME")
  private Date applicationDate;

  @Enumerated(EnumType.STRING)
  private ApplicationStatus status;

  public Long getJobId() {
    return job != null ? job.getId() : null;
  }

  public Long getUserId() {
    return user != null ? user.getId() : null;
  }
}
