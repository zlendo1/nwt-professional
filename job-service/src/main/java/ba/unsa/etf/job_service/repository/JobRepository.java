package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.enums.EmploymentType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

  // Fetch jobs by location with pagination and eager load company data
  @EntityGraph(attributePaths = "company")
  Page<Job> findByLocation(String location, Pageable pageable);

  // Fetch jobs by employment type with pagination and eager load company data
  @EntityGraph(attributePaths = "company")
  Page<Job> findByEmploymentType(
      @Param("employmentType") EmploymentType employmentType, Pageable pageable);

  // Fetch jobs by title, location, and company name with pagination and eager load company data
  @EntityGraph(attributePaths = "company")
  Page<Job>
      findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
          String jobTitle, String location, String companyName, Pageable pageable);

  // Fetch jobs by title and location with pagination and eager load company data
  @EntityGraph(attributePaths = "company")
  Page<Job> findByTitleContainingAndLocationContaining(
      String title, String location, Pageable pageable);

  // Fetch jobs by title with pagination and eager load company data
  @EntityGraph(attributePaths = "company")
  Page<Job> findByTitleContaining(String title, Pageable pageable);

  @EntityGraph(
      attributePaths =
          "company") // Fetch jobs by location with pagination and eager load company data
  Page<Job> findByLocationContaining(String location, Pageable pageable);

  @EntityGraph(attributePaths = "company")
  Optional<Job> findByJobUUID(String jobUUID);
}
