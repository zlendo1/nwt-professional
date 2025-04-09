package ba.unsa.etf.test_skills_service.service;

// import org.springframework.security.crypto.password.PasswordEncoder; // Import za heširanje
import ba.unsa.etf.test_skills_service.dto.user.CreateUserRequest;
import ba.unsa.etf.test_skills_service.dto.user.UpdateUserRequest;
import ba.unsa.etf.test_skills_service.dto.user.UserDto;
import ba.unsa.etf.test_skills_service.mapper.UserMapper;
import ba.unsa.etf.test_skills_service.model.User;
import ba.unsa.etf.test_skills_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper; // Inject Mapper

  // private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

  // Constructor Injection
  public UserService(
      UserRepository userRepository, UserMapper userMapper /*, PasswordEncoder passwordEncoder */) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    // this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::toUserDto) // Mapiraj svaki User u UserDto
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserById(Long id) {
    return userRepository
        .findById(id)
        .map(userMapper::toUserDto); // Mapiraj pronađeni User u UserDto
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserByUsername(String username) {
    return userRepository.findByUsername(username).map(userMapper::toUserDto);
  }

  @Transactional
  public UserDto createUser(CreateUserRequest request) {
    // 1. Mapiraj DTO u Entity (bez lozinke)
    User user = userMapper.toUser(request);

    // 2. Heširaj lozinku i postavi je na entitet
    // String hashedPassword = passwordEncoder.encode(request.password());
    // user.setPasswordHash(hashedPassword);
    user.setPasswordHash("hashed_" + request.password()); // Placeholder za heširanje

    // 3. Sačuvaj entitet
    User savedUser = userRepository.save(user);

    // 4. Mapiraj sačuvani entitet nazad u DTO za odgovor
    return userMapper.toUserDto(savedUser);
  }

  @Transactional
  public Optional<UserDto> updateUser(Long id, UpdateUserRequest request) {
    return userRepository
        .findById(id)
        .map(
            existingUser -> {
              // Ručno ažuriraj polja iz DTO-a
              existingUser.setUsername(request.username());
              existingUser.setEmail(request.email());
              existingUser.setFirstName(request.firstName());
              existingUser.setLastName(request.lastName());
              // Ne ažuriramo lozinku ovde
              User updatedUser = userRepository.save(existingUser);
              return userMapper.toUserDto(updatedUser); // Mapiraj u DTO za odgovor
            });
  }

  // deleteUser ostaje isti (radi sa ID-em)
  @Transactional
  public boolean deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
