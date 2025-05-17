package ba.unsa.etf.test_skills_service.repository;

// ApplicationRepository.java
import ba.unsa.etf.test_skills_service.model.Application;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  List<Application> findByUserId(Long userId); // Example custom finder method

  List<Application> findByTestTestId(
      Long testId); // Example custom finder method (use field name from Test entity)

  List<Application> findByUserIdAndTestTestId(Long userId, Long testId); // Example
}
