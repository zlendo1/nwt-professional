package ba.unsa.etf.communication_service.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "conversation")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user1_id", nullable = false)
  private User user1;

  @ManyToOne
  @JoinColumn(name = "user2_id", nullable = false)
  private User user2;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Message> messages = new HashSet<>();
}
