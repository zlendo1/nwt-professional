package ba.unsa.etf.content_service.entity;

import jakarta.persistence.*;
// Eventualno importi za druga polja ako ih želiš (npr. username)

@Entity
@Table(name = "users") // Pobrini se da se tablica zove 'users' ili kako već treba
public class User {

  @Id
  // NE @GeneratedValue, jer ID dolazi od user-management-service-a
  private Long id;

  // Opcionalno: možeš dodati osnovna polja koja želiš sinkronizirati/cacheirati
  // Npr. ako često trebaš username za neku logiku unutar content-servicea
  // private String username;
  // private String firstName;
  // private String lastName;

  // Konstruktori, getteri, setteri
  public User() {}

  public User(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // Getteri i setteri za opcionalna polja ako ih dodaš
  // public String getUsername() { return username; }
  // public void setUsername(String username) { this.username = username; }
}