package ba.unsa.etf.job_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyUUID;
    private String name;
}
