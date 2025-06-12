package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
  Page<Conversation> findByName(String name, Pageable pageable);

  @Query("select c from Conversation c join c.users u where u.id = :userId")
  Page<Conversation> findByUser_Id(Long userId, Pageable pageable);
}
