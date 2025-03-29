package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserId(Long userId);
    List<Application> findByJobId(Long jobId);
}

