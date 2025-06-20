package ba.unsa.etf.user_management_service.event.init;

import ba.unsa.etf.user_management_service.event.service.UserEventPublisher;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserSyncInitializer {
  private final UserRepository userRepository;
  private final UserEventPublisher userEventPublisher;

  @PostConstruct
  public void handleApplicationReady() {
    log.info("Application startup complete - publishing existing users");

    try {
      List<User> allUsers = userRepository.findAll();
      userEventPublisher.publishAllUsersOnStartup(allUsers);
      log.info("Successfully published {} existing users on startup", allUsers.size());
    } catch (Exception e) {
      log.error("Failed to publish users on startup: {}", e.getMessage(), e);
    }
  }
}
