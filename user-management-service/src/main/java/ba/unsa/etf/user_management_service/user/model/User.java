package ba.unsa.etf.user_management_service.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String passwordHashed;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String role;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = String.valueOf(UUID.randomUUID());  // Generate the UUID only once
        }
    }

}
