package ba.unsa.etf.communication_service.dto.user;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerUserDTO {
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
