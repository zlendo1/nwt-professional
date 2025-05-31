package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  @EntityGraph(attributePaths = {"user"})
  Page<Message> findByConversation_Id(Long conversationId, Pageable pageable);

  Page<Message> findByUser_IdAndConversation_Id(
      Long userId, Long conversationId, Pageable pageable);
}
