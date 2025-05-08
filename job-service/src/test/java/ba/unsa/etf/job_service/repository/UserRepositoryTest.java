package ba.unsa.etf.job_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.job_service.model.SavedSearch;
import ba.unsa.etf.job_service.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class SavedSearchRepositoryNPlusOneTest {

  @Autowired private SavedSearchRepository savedSearchRepository;

  @Autowired private UserRepository userRepository;

  @PersistenceContext private EntityManager entityManager;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setFirstName("Alice");
    user.setLastName("Wonderland");
    user.setUserUUID("ALIC-TEST-UUID");
    user = userRepository.save(user);

    for (int i = 0; i < 5; i++) {
      SavedSearch search = new SavedSearch();
      search.setKeywords("query " + i);
      search.setUser(user);
      entityManager.persist(search);
    }

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  void testFindByUserIdWithEntityGraph() {
    Session session = entityManager.unwrap(Session.class);
    Statistics stats = session.getSessionFactory().getStatistics();
    stats.clear();

    Page<SavedSearch> page =
        savedSearchRepository.findByUser_Id(user.getId(), PageRequest.of(0, 10));

    assertThat(page.getContent()).hasSize(5);
    for (SavedSearch search : page.getContent()) {
      assertThat(search.getUser().getFirstName()).isEqualTo("Alice");
    }

    long entityFetchCount = stats.getEntityFetchCount();

    // Expect no N+1 behavior: should fetch SavedSearch + 1 User
    assertThat(entityFetchCount).isLessThanOrEqualTo(6); // 5 searches + 1 user
  }
}
