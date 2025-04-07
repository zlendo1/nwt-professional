package ba.unsa.etf.communication_service.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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

  @ManyToOne
  @JoinColumn(name = "user_conversation_id", nullable = false)
  private UserConversation userConversation;

  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "encoding", nullable = false)
  private String encoding;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
