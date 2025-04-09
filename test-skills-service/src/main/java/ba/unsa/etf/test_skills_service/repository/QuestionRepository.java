package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  // Find questions for a test, sorted by questionOrder
  List<Question> findByTestTestIdOrderByQuestionOrderAsc(Long testId);
}
