package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class AnswerOfApplicant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "applicant_test_result_id")
  private ApplicantTestResult applicantTestResult;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @ManyToOne
  @JoinColumn(name = "chosen_answer_option_id")
  private AnswerOption chosenAnswerOption;

  private LocalDateTime answeredAt;
}
