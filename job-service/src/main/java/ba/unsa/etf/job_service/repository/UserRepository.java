package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserUUID(String userUUID);
}
