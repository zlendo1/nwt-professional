package ba.unsa.etf.communication_service.event.listener;

import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.event.model.UserEvent;
import ba.unsa.etf.communication_service.repository.UserRepository;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@AllArgsConstructor
@Slf4j
@Component
public class UserEventListener {
  private UserRepository userRepository;

  @Bean
  public Consumer<UserEvent> userEvents() {
    return this::handleUserEvent;
  }

  public void handleUserEvent(UserEvent event) {
    switch (event.getEventType()) {
      case "CREATE" -> {
        User newUser = getEventUser(event);
        userRepository.save(newUser);
        log.info("Created user via event: {}", newUser.getEmail());
      }
      case "UPDATE" -> {
        User existingUser = userRepository.findByEmail(event.getEmail()).orElse(null);
        if (existingUser != null) {
          userRepository.save(existingUser);
          log.info("Updated user via event: {}", existingUser.getEmail());
        } else {
          log.warn("Attempted to update non-existent user with email: {}", event.getEmail());
        }
      }
      case "DELETE" -> {
        String email = event.getEmail();
        userRepository
            .findByEmail(email)
            .ifPresent(
                user -> {
                  userRepository.deleteById(user.getId());
                  log.info("Deleted user via event: {}", email);
                });
      }
      case "STARTUP_SYNC" -> {
        User newUser = getEventUser(event);
        userRepository.save(newUser);
        log.info("Sync recieved and created user: {}", newUser.getEmail());
      }
      default -> {
        log.warn("Received unknown event type: {}", event.getEventType());
      }
    }
  }

  public User getEventUser(UserEvent event) {
    User user = new User();
    user.setEmail(event.getEmail());
    return user;
  }
}
