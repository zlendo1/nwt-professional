package ba.unsa.etf.communication_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Conversation")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;
}
