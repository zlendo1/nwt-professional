package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Recommendation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
  List<Recommendation> findByUserId(Long userId);
}
