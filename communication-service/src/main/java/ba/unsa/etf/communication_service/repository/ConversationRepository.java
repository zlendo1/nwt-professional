package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
  @EntityGraph(attributePaths = {"user1", "user2"})
  @Query("select c from Conversation c where c.user1.id = :userId or c.user2.id = :userId")
  Page<Conversation> findByUser_Id(Long userId, Pageable pageable);

  @Query("select c from Conversation c where c.user1.id = :userId or c.user2.id = :userId")
  boolean existsByUser_Id(Long userId);
}
