package ba.unsa.etf.user_management_service.event.model;

import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
  private String eventType;
  private String eventId;
  private LocalDateTime timestamp;
  private String source;
  private Long id;
  private String uuid;
  private String email;
  private String passwordHashed;
  private String firstName;
  private String lastName;
  private Date dateOfBirth;
  private String profilePicture;
  private String role;
}
