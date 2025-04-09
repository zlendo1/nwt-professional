package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long questionId; // Primary key for the question

  @ManyToOne(fetch = FetchType.LAZY) // Relationship to the test this question belongs to
  @JoinColumn(name = "test_id", nullable = false) // Foreign key column name in this table
  private Test test;

  @Lob
  @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
  private String questionText;

  @Column(name = "question_order")
  private Integer questionOrder; // Order of the question within the test (optional)

  @Column(nullable = false, precision = 4, scale = 2) // e.g., 99.99 points
  private BigDecimal points = BigDecimal.ONE; // Default 1 point

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // Relationship to the answers for this question
  @OneToMany(
      mappedBy = "question",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Answer> answers = new ArrayList<>();

  // Helper method to add an answer
  public void addAnswer(Answer answer) {
    answers.add(answer);
    answer.setQuestion(this);
  }

  public void removeAnswer(Answer answer) {
    answers.remove(answer);
    answer.setQuestion(null);
  }
}
