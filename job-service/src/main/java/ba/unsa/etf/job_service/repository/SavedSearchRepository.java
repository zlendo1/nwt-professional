package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.SavedSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
    Page<SavedSearch> findByUser_Id(Long userId, Pageable pageable);

}
