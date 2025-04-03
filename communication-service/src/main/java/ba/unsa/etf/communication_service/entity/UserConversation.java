package ba.unsa.etf.communication_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserConversation")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConversation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;
}
