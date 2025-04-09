package ba.unsa.etf.test_skills_service.repository;

// TestRepository.java
import ba.unsa.etf.test_skills_service.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {}
