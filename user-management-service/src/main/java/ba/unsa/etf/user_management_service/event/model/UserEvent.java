package ba.unsa.etf.user_management_service.event.model;

import ba.unsa.etf.user_management_service.user.model.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
  private String eventType;
  private User user;
  private String eventId;
  private LocalDateTime timestamp;
  private String source;
}
