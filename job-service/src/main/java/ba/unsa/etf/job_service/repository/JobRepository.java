package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.enums.EmploymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

  @EntityGraph(attributePaths = "company")
  Page<Job> findByLocation(String location, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Page<Job> findByEmploymentType(
      @Param("employmentType") EmploymentType employmentType, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Page<Job>
      findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
          String jobTitle, String location, String companyName, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Page<Job> findByTitleContainingAndLocationContaining(
      String title, String location, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Page<Job> findByTitleContaining(String title, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Page<Job> findByLocationContaining(String location, Pageable pageable);
}
