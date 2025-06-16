package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long testId);
}