package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
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
@Table(name = "tests")
public class Test { // Renamed class from Test to avoid conflict with annotations if needed

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "test_id")
  private Long testId; // Primary key for the test

  @Column(nullable = false, length = 255)
  private String title;

  @Lob // For longer text content (TEXT type in the database)
  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "duration_minutes")
  private Integer durationMinutes; // Estimated test duration in minutes

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // Relationship to the questions of this test
  @OneToMany(
      mappedBy = "test",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Question> questions = new ArrayList<>();

  // Helper method to add a question (maintains relationship consistency)
  public void addQuestion(Question question) {
    questions.add(question);
    question.setTest(this);
  }

  public void removeQuestion(Question question) {
    questions.remove(question);
    question.setTest(null);
  }
}
