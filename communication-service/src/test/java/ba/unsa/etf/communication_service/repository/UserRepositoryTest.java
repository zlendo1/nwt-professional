package ba.unsa.etf.communication_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @PersistenceContext private EntityManager entityManager;

  @BeforeEach
  public void setUp() {
    User user1 = new User();
    user1.setUsername("user1");
    user1.setEmail("user1@example.com");

    User user2 = new User();
    user2.setUsername("user2");
    user2.setEmail("user2@example.com");

    Conversation conversation1 = new Conversation();
    conversation1.setName("Test Conversation 1");
    conversation1.getUsers().add(user1);
    conversation1.getUsers().add(user2);

    Conversation conversation2 = new Conversation();
    conversation2.setName("Test Conversation 2");
    conversation2.getUsers().add(user1);

    user1.getConversations().add(conversation1);
    user1.getConversations().add(conversation2);
    user2.getConversations().add(conversation1);

    entityManager.persist(user1);
    entityManager.persist(user2);
    entityManager.persist(conversation1);
    entityManager.persist(conversation2);

    entityManager.flush();
    entityManager.clear();
  }

  // @Test
  public void testFindByUsername_NoNPlusOneProblem() {
    Statistics statistics =
        entityManager
            .getEntityManagerFactory()
            .unwrap(org.hibernate.SessionFactory.class)
            .getStatistics();
    statistics.setStatisticsEnabled(true);
    statistics.clear();

    User user = userRepository.findByUsername("user1").orElseThrow();

    for (Conversation conversation : user.getConversations()) {
      conversation.getId();
    }

    long queryCount = statistics.getEntityFetchCount();
    assertThat(queryCount).isEqualTo(1);
  }
}
