package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.Recommendation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @EntityGraph(attributePaths = {"job", "user"})
    List<Recommendation> findByUser_Id(Long userId);
    @EntityGraph(attributePaths = {"job", "user"})
    List<Recommendation> findByJob(Job job);
}
