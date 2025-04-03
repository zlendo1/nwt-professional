package ba.unsa.etf.communication_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Message")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
