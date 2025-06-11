package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}
