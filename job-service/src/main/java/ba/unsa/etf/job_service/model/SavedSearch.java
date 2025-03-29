package ba.unsa.etf.job_service.model;

import ba.unsa.etf.job_service.model.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "SavedSearches")
public class SavedSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String keywords;
    private String location;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private Date saveDate;
}
