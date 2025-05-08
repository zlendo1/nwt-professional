package ba.unsa.etf.job_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.job_service.model.Company;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.enums.EmploymentType;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JobRepositoryTest {

  @Autowired private JobRepository jobRepository;
  @Autowired private CompanyRepository companyRepository;
  @Autowired private EntityManager entityManager;

  @BeforeEach
  @Transactional
  void setUp() {
    Company company = new Company();
    company.setName("TestCompany");
    companyRepository.save(company);

    for (int i = 0; i < 5; i++) {
      Job job = new Job();
      job.setTitle("Developer");
      job.setLocation("Sarajevo");
      job.setEmploymentType(EmploymentType.FULL_TIME);
      job.setCompany(company);
      jobRepository.save(job);
    }

    entityManager.flush();
    entityManager.clear();
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().clear();
  }

  @Test
  @Transactional
  void shouldNotHaveNPlusOneProblemWhenFetchingByEmploymentTypeWithPagination() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    // Use pagination (Pageable)
    Pageable pageable = Pageable.ofSize(5);
    Page<Job> jobsPage = jobRepository.findByEmploymentType(EmploymentType.FULL_TIME, pageable);

    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    // Expecting 1 query for jobs and 1 query for company (eagerly fetched)
    assertThat(queryCount).isLessThanOrEqualTo(2);
  }

  @Test
  @Transactional
  void shouldNotHaveNPlusOneProblemWhenFetchingByLocationWithPagination() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    // Use pagination (Pageable)
    Pageable pageable = Pageable.ofSize(5);
    Page<Job> jobsPage = jobRepository.findByLocation("Sarajevo", pageable);

    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    // Expecting 1 query for jobs and 1 query for company (eagerly fetched)
    assertThat(queryCount).isLessThanOrEqualTo(2);
  }

  @Test
  @Transactional
  void shouldNotHaveNPlusOneProblemWhenFetchingByTitleAndLocationWithPagination() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    // Use pagination (Pageable)
    Pageable pageable = Pageable.ofSize(5);
    Page<Job> jobsPage =
        jobRepository.findByTitleContainingAndLocationContaining("Developer", "Sarajevo", pageable);

    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    // Expecting 1 query for jobs and 1 query for company (eagerly fetched)
    assertThat(queryCount).isLessThanOrEqualTo(2);
  }

  @Test
  @Transactional
  void shouldNotHaveNPlusOneProblemWhenFetchingByTitleWithPagination() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    // Use pagination (Pageable)
    Pageable pageable = Pageable.ofSize(5);
    Page<Job> jobsPage = jobRepository.findByTitleContaining("Developer", pageable);

    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    // Expecting 1 query for jobs and 1 query for company (eagerly fetched)
    assertThat(queryCount).isLessThanOrEqualTo(2);
  }

  @Test
  @Transactional
  void shouldNotHaveNPlusOneProblemWhenFetchingByTitleLocationAndCompanyNameWithPagination() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    // Use pagination (Pageable)
    Pageable pageable = Pageable.ofSize(5);
    Page<Job> jobsPage =
        jobRepository
            .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
                "Developer", "Sarajevo", "TestCompany", pageable);

    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    // Expecting 1 query for jobs and 1 query for company (eagerly fetched)
    assertThat(queryCount).isLessThanOrEqualTo(2);
  }
}
