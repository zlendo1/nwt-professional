package ba.unsa.etf.content_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

  private Long id;           // Odgovara JSON ključu "id"
  private String uuid;         // Odgovara JSON ključu "uuid" (ako ti treba)
  private String email;        // Odgovara JSON ključu "email"
  private String firstName;    // Odgovara JSON ključu "firstName" (UMJESTO tvog starog 'username')
  private String lastName;     // Odgovara JSON ključu "lastName" (UMJESTO tvog starog 'userlastname')
  private LocalDate dateOfBirth; // Odgovara JSON ključu "dateOfBirth" (Jackson će string "YYYY-MM-DD" mapirati u LocalDate)
  private String profilePicture; // Odgovara JSON ključu "profilePicture"
  private String role;         // Odgovara JSON ključu "role" (ako ti treba)


  // ----------- Generiraj Gettere i Settere za SVA GORNJA POLJA -----------
  // Primjer za nekoliko, trebaš za sva:
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(Long id) {
    this.uuid = uuid;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
