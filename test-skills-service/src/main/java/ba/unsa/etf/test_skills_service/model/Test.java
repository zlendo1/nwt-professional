package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "job_uuid")
  private String jobUUID;

  private String name;
  private String type;
  private Integer timeLimitMinutes;
  private String description;

  @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions;

  @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
  private List<ApplicantTestResult> results;
}
