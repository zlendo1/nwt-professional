package ba.unsa.etf.job_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.job_service.model.*;
import ba.unsa.etf.job_service.model.enums.ApplicationStatus;
import ba.unsa.etf.job_service.model.enums.EmploymentType;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ApplicationRepositoryTest {

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private JobRepository jobRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CompanyRepository companyRepository;

  @Autowired private EntityManager entityManager;

  private Company company;
  private User user;
  private Job job;
  private Application application;

  @BeforeEach
  void setup() {
    company = new Company();
    company.setName("TestCompany");
    companyRepository.save(company);

    user = new User();
    user.setFirstName("Test");
    user.setLastName("User");
    user.setUserUUID("USER-UUID-TEST");
    userRepository.save(user);

    job = new Job();
    job.setTitle("Test Job");
    job.setLocation("Sarajevo");
    job.setEmploymentType(EmploymentType.FULL_TIME);
    job.setCompany(company);
    jobRepository.save(job);

    application = new Application();
    application.setUser(user);
    application.setJob(job);
    application.setStatus(ApplicationStatus.PENDING);
    application.setApplicationDate(new Date());
    applicationRepository.save(application);

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  void shouldFindByUserIdWithJobAndUserFetched() {
    List<Application> apps = applicationRepository.findByUser_Id(user.getId());

    assertThat(apps).hasSize(1);
    assertThat(apps.get(0).getUser().getFirstName()).isEqualTo("Test");
    assertThat(apps.get(0).getJob().getTitle()).isEqualTo("Test Job");
  }

  @Test
  void shouldFindByJobIdWithJobAndUserFetched() {
    List<Application> apps = applicationRepository.findByJob_Id(job.getId());

    assertThat(apps).hasSize(1);
    assertThat(apps.get(0).getUser().getFirstName()).isEqualTo("Test");
    assertThat(apps.get(0).getJob().getTitle()).isEqualTo("Test Job");
  }

  @Test
  void shouldFindByCompanyIdWithJobAndUserFetched() {
    List<Application> apps = applicationRepository.findByJob_Company_Id(company.getId());

    assertThat(apps).hasSize(1);
    assertThat(apps.get(0).getUser().getFirstName()).isEqualTo("Test");
    assertThat(apps.get(0).getJob().getCompany().getName()).isEqualTo("TestCompany");
  }

  @Test
  void shouldNotHaveNPlusOneProblemWhenFetchingByCompanyId() {
    Session session = entityManager.unwrap(Session.class);
    session.getSessionFactory().getStatistics().clear();
    session.getSessionFactory().getStatistics().setStatisticsEnabled(true);

    List<Application> apps = applicationRepository.findByJob_Company_Id(company.getId());
    long queryCount = session.getSessionFactory().getStatistics().getPrepareStatementCount();

    assertThat(apps).hasSize(1);
    assertThat(queryCount).isLessThanOrEqualTo(2); // 1 for applications, 1 for join fetch
  }
}
