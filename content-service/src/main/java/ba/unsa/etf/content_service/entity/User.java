package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String userlastname;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private LocalDate regdate;

  // Constructor default
  public User() {}

  // Getteri i setteri

  // id
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // username
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // lastname
  public String getUserlastname() {
    return userlastname;
  }

  public void setUserlastname(String userlastname) {
    this.userlastname = userlastname;
  }

  // email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // pass
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
