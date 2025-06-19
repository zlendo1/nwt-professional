package ba.unsa.etf.user_management_service.user.service;

import ba.unsa.etf.user_management_service.event.service.UserEventPublisher;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserEventPublisher userEventPublisher;

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> getUserByUuid(String uuid) {
    return userRepository.findByUuid(uuid);
  }

  public void createUser(User user) {
    User savedUser = userRepository.save(user);

    userEventPublisher.publishUserCreatedEvent(savedUser);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
