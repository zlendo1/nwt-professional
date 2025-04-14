package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @EntityGraph(attributePaths = {"job", "user"})
    List<Application> findByUser_Id(Long userId);
    @EntityGraph(attributePaths = {"job", "user"})
    List<Application> findByJob_Id(Long jobId);
    @EntityGraph(attributePaths = {"job", "user"})
    List<Application> findByJob_Company_Id(Long companyId);
}

