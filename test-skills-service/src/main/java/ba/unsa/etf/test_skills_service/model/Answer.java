package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long answerId; // Primary key for the answer

  @ManyToOne(fetch = FetchType.LAZY) // Relationship to the question this answer belongs to
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Lob
  @Column(name = "answer_text", nullable = false, columnDefinition = "TEXT")
  private String answerText;

  @Column(name = "is_correct", nullable = false)
  private Boolean isCorrect = false; // Default is not correct

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
