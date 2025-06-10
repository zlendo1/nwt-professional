package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "test_id")
  private Test test;

  @Column(columnDefinition = "TEXT")
  private String questionText;

  private Integer points;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AnswerOption> answerOptions;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<AnswerOfApplicant> applicantAnswers;
}
