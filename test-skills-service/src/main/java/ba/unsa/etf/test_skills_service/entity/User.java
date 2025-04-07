package ba.unsa.etf.test_skills_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Optional: specify the table name if it differs from the class name
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  // Constructors, getters, and setters

  public User() {}

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
