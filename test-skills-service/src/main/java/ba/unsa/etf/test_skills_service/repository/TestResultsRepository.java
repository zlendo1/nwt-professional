package ba.unsa.etf.test_skills_service.repository;

import ba.unsa.etf.test_skills_service.model.ApplicantTestResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultsRepository extends JpaRepository<ApplicantTestResult, Long> {
  List<ApplicantTestResult> findByTestId(Long testId);

  List<ApplicantTestResult> findByApplicationId(Long applicationId);
}
