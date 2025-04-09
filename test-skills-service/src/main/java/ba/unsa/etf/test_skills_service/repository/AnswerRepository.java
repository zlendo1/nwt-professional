package ba.unsa.etf.test_skills_service.repository; // AnswerRepository.java

import ba.unsa.etf.test_skills_service.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {}
