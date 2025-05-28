package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  @Query("select u from User u join u.conversations c where c.id = :conversationId")
  List<User> findByConversation_Id(Long conversationId);
}
