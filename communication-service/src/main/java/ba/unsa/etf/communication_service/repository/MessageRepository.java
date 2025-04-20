package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findByConversation(Long conversationId);

  List<Message> findByUserAndConversation(Long userId, Long conversationId);
}
