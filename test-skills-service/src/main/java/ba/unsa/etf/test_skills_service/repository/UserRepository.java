package ba.unsa.etf.test_skills_service.repository;

// UserRepository.java
import ba.unsa.etf.test_skills_service.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username); // Example

  Optional<User> findByEmail(String email); // Example
}
