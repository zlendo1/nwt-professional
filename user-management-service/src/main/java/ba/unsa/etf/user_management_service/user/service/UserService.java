package ba.unsa.etf.user_management_service.user.service;

import ba.unsa.etf.user_management_service.event.service.UserEventPublisher;
import ba.unsa.etf.user_management_service.user.dto.UserDTO;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserEventPublisher userEventPublisher;

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> getUserByUuid(String uuid) {
    return userRepository.findByUuid(uuid);
  }

  public void createUser(User user) {
    userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public User registerUser(UserDTO signUpRequest) {
    userRepository
        .findByEmail(signUpRequest.getEmail())
        .ifPresent(
            user -> {
              throw new IllegalStateException(
                  "User with email " + signUpRequest.getEmail() + " already exists.");
            });

    User user = new User();
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setEmail(signUpRequest.getEmail());

    user.setPasswordHashed(passwordEncoder.encode(signUpRequest.getPassword()));

    user.setRole("ROLE_USER");

    User savedUser = userRepository.save(user);

    userEventPublisher.publishUserCreatedEvent(savedUser);

    return savedUser;
  }
}
