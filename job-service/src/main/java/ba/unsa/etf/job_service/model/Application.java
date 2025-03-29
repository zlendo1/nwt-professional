package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Long applicationId;

    @GeneratedValue(strategy = GenerationType.UUID)
    private String applicationUUID;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
