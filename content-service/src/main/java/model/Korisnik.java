package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class Korisnik {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String ime;

  @Column(nullable = false)
  private String prezime;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String lozinka;

  @Column(nullable = false)
  private LocalDate datum_registracije;

  // Konstruktor default
  public Korisnik() {}

  // Getteri i setteri
  // ID
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // Ime
  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  // Prezime
  public String getPrezime() {
    return prezime;
  }

  public void setPrezime(String prezime) {
    this.prezime = prezime;
  }

  // Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // Lozinka
  public String getLozinka() {
    return lozinka;
  }

  public void setLozinka(String lozinka) {
    this.lozinka = lozinka;
  }

  // Datum
  public LocalDate getDatum_registracije() {
    return datum_registracije;
  }

  public void setDatum_registracije(LocalDate datum_registracije) {
    this.datum_registracije = datum_registracije;
  }
}
