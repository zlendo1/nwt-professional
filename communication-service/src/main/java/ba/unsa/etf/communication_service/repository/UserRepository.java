package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u JOIN u.conversations c WHERE c.id = :conversationId")
  List<User> findByConversation_Id(@Param("conversationId") Long conversationId);
}
