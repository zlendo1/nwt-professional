package ba.unsa.etf.content_service.dto;

import java.time.LocalDate;

public class UserDto {

  private Long id;
  private String username;
  private String userlastname;
  private String email;
  private LocalDate regdate;

  // Getters and setters
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

  public String getUserlastname() {
    return userlastname;
  }

  public void setUserlastname(String userlastname) {
    this.userlastname = userlastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getRegdate() {
    return regdate;
  }

  public void setRegdate(LocalDate regdate) {
    this.regdate = regdate;
  }
}
