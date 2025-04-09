package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "application_id")
  private Long applicationId; // Primary key for the application

  @ManyToOne(fetch = FetchType.LAZY) // Relationship to the user
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY) // Relationship to the test
  @JoinColumn(name = "test_id", nullable = false)
  private Test test;

  @Column(name = "job_posting_id")
  private Long jobPostingId; // Assuming this is the ID of another entity (JobPosting)

  @Column(name = "application_date", nullable = false)
  private LocalDate applicationDate = LocalDate.now(); // Default today's date

  @Column(nullable = false, length = 50)
  private String status = "Pending"; // Default status

  // Integrated test result fields
  @Column(name = "test_score", precision = 5, scale = 2) // e.g., 100.00 or 85.50
  private BigDecimal testScore; // Test score obtained (e.g., 85.50)

  @Column(name = "test_status", nullable = false, length = 50)
  private String testStatus =
      "Not Started"; // Default test status, e.g., Not Started, In Progress, Completed

  @Column(name = "test_started_at")
  private LocalDateTime testStartedAt; // Time when the test solving started

  @Column(name = "test_completed_at")
  private LocalDateTime testCompletedAt; // Time when the test solving finished

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
