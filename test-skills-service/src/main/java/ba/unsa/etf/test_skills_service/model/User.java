package ba.unsa.etf.test_skills_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data // Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Generates default constructor
@AllArgsConstructor // Lombok: Generates constructor with all arguments
@Entity
@Table(name = "users") // Explicit table name (good practice)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id; // Primary key for the user

  @Column(unique = true, nullable = false, length = 255)
  private String username;

  @Column(unique = true, nullable = false, length = 255)
  private String email;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash; // Store the hashed password!

  @Column(name = "first_name", length = 100)
  private String firstName;

  @Column(name = "last_name", length = 100)
  private String lastName;

  @CreationTimestamp // Hibernate: Automatically sets the creation time
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public Long getUserId() {
    return id;
  }

  // Optional: Link to the applications of this user (if a bidirectional relationship is needed)
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  // private List<Application> applications;
}
