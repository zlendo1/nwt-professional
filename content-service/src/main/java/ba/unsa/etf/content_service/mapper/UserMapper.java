package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.UserDto; // DTO koji komunicira s user-management-service
import ba.unsa.etf.content_service.entity.User; // Lokalni User entitet (samo s ID-om)
import ba.unsa.etf.content_service.service.UserDataClientService; // Za dohvaćanje punih detalja
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
  private final UserDataClientService userDataClientService;

  @Autowired
  public UserMapper(UserDataClientService userDataClientService) {
    this.userDataClientService = userDataClientService;
  }

  /**
   * Mapira lokalni User entitet (koji ima samo ID) u puni UserDto tako što poziva
   * UserDataClientService da dohvati detalje.
   */
  public UserDto toDto(User userEntity) {
    if (userEntity == null || userEntity.getId() == null) {
      logger.warn("Pokušaj mapiranja User entiteta bez ID-a u UserDto. Vraća se placeholder.");
      // Možeš pozvati userDataClientService.fetchUserById(null) da dobiješ standardni placeholder
      return userDataClientService.fetchUserById(null);
    }
    // Dohvati pune detalje o korisniku iz user-management-service
    return userDataClientService.fetchUserById(userEntity.getId());
  }

  /**
   * Mapira UserDto (koji dolazi iz user-management-service ili kao zahtjev) u lokalni User entitet
   * (koji će imati samo ID). Koristi se npr. za kreiranje lokalne reference.
   */
  public User toEntity(UserDto userDto) {
    if (userDto == null || userDto.getId() == null) {
      logger.warn("Pokušaj mapiranja UserDto bez ID-a u User entitet. Vraća se null.");
      // Možeš odlučiti baciti iznimku ako je ID obavezan
      return null;
    }
    User userEntity = new User();
    userEntity.setId(userDto.getId());
    // Ne mapiramo druga polja jer naš lokalni User entitet ima samo ID
    return userEntity;
  }
}
