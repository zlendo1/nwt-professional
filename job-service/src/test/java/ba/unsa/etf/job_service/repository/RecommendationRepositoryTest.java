package ba.unsa.etf.job_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.job_service.model.Company;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.Recommendation;
import ba.unsa.etf.job_service.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RecommendationRepositoryNPlusOneTest {

  @Autowired private RecommendationRepository recommendationRepository;

  @Autowired private UserRepository userRepository;
  @Autowired private CompanyRepository companyRepository;
  @Autowired private JobRepository jobRepository;

  @PersistenceContext private EntityManager entityManager;

  private User user;
  private Job job;

  @BeforeEach
  void setup() {
    user = new User();
    user.setFirstName("Kick");
    user.setLastName("Buttowski");
    user.setUserUUID("KICK-USER-UUID");
    user = userRepository.save(user);

    Company company = new Company();
    company.setName("TestCompany");
    companyRepository.save(company);

    job = new Job();
    job.setTitle("Engineer");
    job.setCompany(company);
    entityManager.persist(job);

    for (int i = 1; i <= 5; i++) {
      Recommendation rec = new Recommendation();
      rec.setUser(user);
      rec.setJob(job);
      entityManager.persist(rec);
    }

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  void testEntityGraphAvoidsNPlusOne() {
    Session session = entityManager.unwrap(Session.class);
    Statistics stats = session.getSessionFactory().getStatistics();
    stats.clear();

    List<Recommendation> results = recommendationRepository.findByUser_Id(user.getId());

    assertThat(results).hasSize(5);
    for (Recommendation r : results) {
      assertThat(r.getUser().getFirstName()).isEqualTo("Kick");
      assertThat(r.getJob().getTitle()).isEqualTo("Engineer");
    }

    long entityFetchCount = stats.getEntityFetchCount();
    long jobLoadCount = stats.getEntityLoadCount();

    // Verifies batch fetching is not done per Recommendation (N+1)
    assertThat(entityFetchCount).isLessThanOrEqualTo(7); // 5 recs + 1 job + 1 user
    assertThat(jobLoadCount).isLessThanOrEqualTo(7);
  }
}
