package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.SavedSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
    List<SavedSearch> findByUserId(Long userId);
}
