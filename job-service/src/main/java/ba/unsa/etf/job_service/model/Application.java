package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String applicationUUID = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "DATETIME")
    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
