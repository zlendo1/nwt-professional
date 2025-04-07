package ba.unsa.etf.job_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommendation")
public class Recommendation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "job_id", nullable = false)
  private Job job;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
