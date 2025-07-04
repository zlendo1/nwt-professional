package ba.unsa.etf.user_management_service.event.service;

import ba.unsa.etf.user_management_service.event.model.UserEvent;
import ba.unsa.etf.user_management_service.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventPublisher {

  private final StreamBridge streamBridge;
  private static final String USER_EVENTS_BINDING = "userEvents-out-0";

  public void publishUserCreatedEvent(User user) {
    UserEvent event = createEvent("CREATE", user);
    publishEvent(event);
    log.info("Published USER_CREATED event for user: {}", user.getEmail());
  }

  public void publishUserUpdatedEvent(User user) {
    UserEvent event = createEvent("UPDATE", user);
    publishEvent(event);
    log.info("Published USER_UPDATED event for user: {}", user.getEmail());
  }

  public void publishUserDeletedEvent(User user) {
    UserEvent event = createEvent("DELETE", user);
    publishEvent(event);
    log.info("Published USER_DELETED event for user: {}", user.getEmail());
  }

  public void publishAllUsersOnStartup(List<User> users) {
    for (User user : users) {
      UserEvent event = createEvent("STARTUP_SYNC", user);
      publishEvent(event);
    }
    log.info("Published {} user events on startup", users.size());
  }

  private UserEvent createEvent(String eventType, User user) {
    UserEvent event = new UserEvent();

    event.setEventType(eventType);
    event.setEventId(UUID.randomUUID().toString());
    event.setTimestamp(LocalDateTime.now());
    event.setSource("user-management-service");
    event.setId(user.getId());
    event.setUuid(user.getUuid());
    event.setEmail(user.getEmail());
    event.setPasswordHashed(user.getPasswordHashed());
    event.setFirstName(user.getFirstName());
    event.setLastName(user.getLastName());
    event.setDateOfBirth(user.getDateOfBirth());
    event.setProfilePicture(user.getProfilePicture());
    event.setRole(user.getRole());

    return event;
  }

  private void publishEvent(UserEvent event) {
    try {
      streamBridge.send(USER_EVENTS_BINDING, event);
    } catch (Exception e) {
      log.error("Failed to publish user event: {}", e.getMessage(), e);
    }
  }
}
