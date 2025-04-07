package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.SavedSearch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
  List<SavedSearch> findByUserId(Long userId);
}
