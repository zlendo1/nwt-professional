package ba.unsa.etf.job_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
