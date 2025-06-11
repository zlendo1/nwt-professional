package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.AnswerOfApplicant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantAnswerRepository extends JpaRepository<AnswerOfApplicant, Long> {
  List<AnswerOfApplicant> findByApplicantTestResultId(Long resultId);

  List<AnswerOfApplicant> findByQuestionId(Long questionId);
}
