package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.enums.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByLocation(String location);
    List<Job> findByEmploymentType(EmploymentType employmentType);
    List<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
            String jobTitle, String location, String companyName);
}

