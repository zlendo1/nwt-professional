package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.client.UserManagementClient;
import ba.unsa.etf.content_service.dto.UserDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Označava da je ovo Spring servisni bean
public class UserDataClientService {

  private static final Logger logger = LoggerFactory.getLogger(UserDataClientService.class);

  private final UserManagementClient userManagementClient; // Injektiramo Feign klijent

  // Konstruktorska injekcija je preporučena
  @Autowired
  public UserDataClientService(UserManagementClient userManagementClient) {
    this.userManagementClient = userManagementClient;
  }

  /**
   * Dohvaća podatke o korisniku iz user-management-service koristeći Feign klijent.
   *
   * @param userId ID korisnika za kojeg se dohvaćaju podaci.
   * @return UserDto s podacima o korisniku, ili placeholder UserDto ako dođe do greške/korisnik
   *     nije pronađen.
   */
  public UserDto fetchUserById(Long userId) {
    if (userId == null) {
      logger.warn("Pokušaj dohvaćanja korisnika s null ID-om. Vraća se placeholder.");
      return createPlaceholderUserDto();
    }

    logger.info("Dohvaćam korisnika s ID: {} putem UserManagementClient-a.", userId);
    try {
      // Poziv metode definirane u UserManagementClient sučelju
      UserDto user = userManagementClient.getUserById(userId);

      if (user != null
          && user.getId() != null) { // Dodatna provjera da smo dobili validnog korisnika
        logger.info(
            "Uspješno dohvaćen korisnik: ID={}, Ime={}, Prezime={}",
            user.getId(),
            user.getFirstName(),
            user.getLastName());
      } else {
        logger.warn(
            "UserManagementClient vratio null ili korisnika s null ID-om za traženi ID: {}. Vraća se placeholder.",
            userId);
        return createPlaceholderUserDto();
      }
      return user;
    } catch (FeignException.NotFound ex) {
      // Hvatanje specifične 404 greške od Feign-a
      logger.error(
          "Korisnik s ID: {} nije pronađen u user-management-service (404). Feign poruka: {}",
          userId,
          ex.getMessage());
      return createPlaceholderUserDto();
    } catch (FeignException ex) {
      // Hvatanje ostalih Feign grešaka (npr. 5xx, timeout, konekcija odbijena)
      // ex.contentUTF8() može dati tijelo odgovora ako postoji, korisno za debugiranje
      logger.error(
          "Feign greška pri dohvaćanju korisnika s ID: {}. Status: {}, Tijelo odgovora (ako postoji): {}, Poruka: {}",
          userId,
          ex.status(),
          ex.contentUTF8(),
          ex.getMessage(),
          ex);
      return createPlaceholderUserDto();
    } catch (Exception ex) {
      // Hvatanje bilo koje druge neočekivane greške
      logger.error(
          "Neočekivana greška pri dohvaćanju korisnika s ID: {}. Poruka: {}",
          userId,
          ex.getMessage(),
          ex);
      return createPlaceholderUserDto();
    }
  }

  /**
   * Pomoćna metoda za kreiranje placeholder UserDto objekta u slučaju greške ili ako korisnik nije
   * pronađen.
   *
   * @return UserDto s defaultnim vrijednostima za nepoznatog korisnika.
   */
  private UserDto createPlaceholderUserDto() {
    UserDto placeholder = new UserDto();
    placeholder.setId(null); // Ili -1L da se jasno vidi da je placeholder
    placeholder.setFirstName("Nepoznat");
    placeholder.setLastName("Korisnik");
    // Možeš dodati i defaultnu profilnu sliku ako je imaš
    // placeholder.setProfilePicture("url/do/default/avatara.png");
    logger.warn("Kreiran i vraćen placeholder UserDto.");
    return placeholder;
  }
}
