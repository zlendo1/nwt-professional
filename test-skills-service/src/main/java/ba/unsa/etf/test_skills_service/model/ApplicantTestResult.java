package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class ApplicantTestResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long applicationId; // ID aplikanta iz drugog mikroservisa

  @ManyToOne
  @JoinColumn(name = "test_id")
  private Test test;

  private Double score;
  private Boolean passed;
  private LocalDateTime completionDate;

  @OneToMany(mappedBy = "applicantTestResult", cascade = CascadeType.ALL)
  private List<AnswerOfApplicant> answers;
}
