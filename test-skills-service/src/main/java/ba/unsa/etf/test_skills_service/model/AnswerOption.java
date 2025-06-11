package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class AnswerOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @Column(columnDefinition = "TEXT")
  private String optionText;

  private Boolean isCorrect;

  @OneToMany(mappedBy = "chosenAnswerOption")
  private List<AnswerOfApplicant> applicantAnswers;
}
