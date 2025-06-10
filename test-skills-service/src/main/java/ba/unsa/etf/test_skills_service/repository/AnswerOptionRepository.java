package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.AnswerOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
  List<AnswerOption> findByQuestionId(Long questionId);
}
