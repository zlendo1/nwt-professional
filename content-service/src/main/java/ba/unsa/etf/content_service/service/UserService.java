package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.dto.UserDto;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.mapper.UserMapper;
import ba.unsa.etf.content_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /**
   * Dohvaća sve lokalne reference na korisnike i za svaku dobiva pune detalje
   * iz user-management-service putem UserMapper-a.
   * Koristiti s oprezom ako je broj korisnika u lokalnoj bazi velik.
   * @return Lista UserDto objekata s punim detaljima.
   */
  @Transactional(readOnly = true)
  public List<UserDto> getAllUsersWithDetails() {
    logger.info("Dohvaćanje svih lokalnih user referenci i njihovih punih detalja.");
    List<User> localUserReferences = userRepository.findAll();
    if (localUserReferences.isEmpty()) {
      return Collections.emptyList();
    }
    return localUserReferences.stream()
            .map(userMapper::toDto) // userMapper.toDto interno poziva UserDataClientService
            .collect(Collectors.toList());
  }

  /**
   * Dohvaća pune detalje o korisniku na temelju njegovog ID-a,
   * koristeći UserMapper koji interno poziva user-management-service.
   * @param id ID korisnika.
   * @return Optional<UserDto> s punim detaljima, ili Optional.empty() ako korisnik nije pronađen.
   */
  @Transactional(readOnly = true)
  public Optional<UserDto> getUserDetailsById(Long id) {
    logger.info("Dohvaćanje punih detalja za korisnika s ID: {}", id);
    if (id == null) {
      logger.warn("Pokušaj dohvaćanja detalja korisnika s null ID-om.");
      // UserMapper će se pobrinuti za placeholder ako UserDataClientService vrati grešku ili null
      return Optional.ofNullable(userMapper.toDto(null));
    }
    // Kreiramo privremenu User referencu samo s ID-om da bismo pozvali mapper
    User tempUserReference = new User(id);
    UserDto userDetails = userMapper.toDto(tempUserReference);

    // Vraćamo Optional.ofNullable jer userDetails može biti placeholder s null ID-om
    // ili potpuno null ako je došlo do greške koju mapper nije mogao hendlati placeholderom.
    // Ako placeholder ima npr. ID=-1L, onda bi provjera bila drugačija.
    // Za sada, ako userDetails.getId() je null, smatramo da nije validan korisnik.
    if (userDetails != null && userDetails.getId() != null) {
      return Optional.of(userDetails);
    }
    return Optional.empty();
  }

  /**
   * Osigurava da lokalna referenca na korisnika (samo s ID-om) postoji u bazi content-service-a.
   * Ako ne postoji, kreira je.
   * @param userId ID korisnika iz user-management-service-a.
   * @return Lokalni User ENTITET (samo s ID-om).
   */
  @Transactional
  public User ensureUserReferenceExists(Long userId) {
    if (userId == null) {
      logger.error("Pokušaj osiguravanja reference za korisnika s null ID-om.");
      throw new IllegalArgumentException("User ID ne može biti null za kreiranje/dohvaćanje reference.");
    }
    return userRepository.findById(userId)
            .orElseGet(() -> {
              logger.info("Lokalna User referenca za ID: {} ne postoji. Kreiram novu.", userId);
              User newUserReference = new User(userId);
              return userRepository.save(newUserReference);
            });
  }

  // Metode za direktno kreiranje, ažuriranje ili brisanje LOKALNIH referenci.
  // Koristiti s oprezom jer user-management-service je glavni izvor istine.

  /**
   * Kreira LOKALNU REFERENCU na korisnika u content-service bazi.
   * NE KREIRA korisnika u user-management-service-u.
   * Obično se poziva s ID-om korisnika.
   * @param userId ID korisnika za kojeg se kreira lokalna referenca.
   * @return UserDto s punim detaljima dohvaćenim iz user-management-service-a za novokreiranu referencu.
   */
  @Transactional
  public UserDto createLocalUserReferenceById(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("User ID je obavezan za kreiranje lokalne reference.");
    }
    if (userRepository.existsById(userId)) {
      logger.warn("Lokalna referenca za korisnika s ID: {} već postoji.", userId);
      return getUserDetailsById(userId).orElse(null); // Vrati detalje za postojećeg
    }
    User newUserReference = new User(userId);
    userRepository.save(newUserReference);
    logger.info("Kreirana lokalna referenca za korisnika s ID: {}", userId);
    return userMapper.toDto(newUserReference); // Dohvati i vrati pune detalje
  }

  /**
   * Briše LOKALNU REFERENCU na korisnika iz content-service baze.
   * NE BRIŠE korisnika iz user-management-service-a.
   * Potreban oprez zbog mogućih stranih ključeva (npr. postovi koji referenciraju ovog korisnika).
   * @param id ID lokalne korisničke reference za brisanje.
   */
  @Transactional
  public void deleteLocalUserReference(Long id) {
    if (id == null) {
      logger.warn("Pokušaj brisanja lokalne reference s null ID-om.");
      return;
    }
    if (userRepository.existsById(id)) {
      // TODO: Razmotriti logiku za hendlanje postojećih postova/komentara vezanih za ovog korisnika
      // prije brisanja reference (npr. postaviti userId na null ili spriječiti brisanje).
      userRepository.deleteById(id);
      logger.info("Obrisana lokalna referenca za korisnika s ID: {}", id);
    } else {
      logger.warn("Pokušaj brisanja nepostojeće lokalne reference za korisnika s ID: {}", id);
    }
  }
}