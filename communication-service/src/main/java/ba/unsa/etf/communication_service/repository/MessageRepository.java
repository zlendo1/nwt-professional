package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  @EntityGraph(attributePaths = {"user"})
  List<Message> findByConversation_Id(Long conversationId);

  List<Message> findByUser_IdAndConversation_Id(Long userId, Long conversationId);
}
