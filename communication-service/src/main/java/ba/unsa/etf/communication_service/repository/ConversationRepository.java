package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Conversation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
  List<Conversation> findByName(String name);

  @Query("SELECT c FROM Conversation JOIN c.users u WHERE u.id = :userId")
  List<Conversation> findByUser_Id(@Param("userId") Long userId);
}
