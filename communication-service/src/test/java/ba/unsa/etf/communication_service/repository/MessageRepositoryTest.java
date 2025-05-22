package ba.unsa.etf.communication_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.entity.Message;
import ba.unsa.etf.communication_service.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MessageRepositoryTest {

  @Autowired private MessageRepository messageRepository;

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

    Message message1 = new Message();
    message1.setUser(user1);
    message1.setConversation(conversation1);
    message1.setContent("Test Message 1");

    Message message2 = new Message();
    message2.setUser(user2);
    message2.setConversation(conversation1);
    message2.setContent("Test Message 2");

    entityManager.persist(user1);
    entityManager.persist(user2);
    entityManager.persist(conversation1);
    entityManager.persist(conversation2);
    entityManager.persist(message1);
    entityManager.persist(message2);

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  public void testFindByConversationId_NoNPlusOneProblem() {
    Statistics statistics =
        entityManager
            .getEntityManagerFactory()
            .unwrap(org.hibernate.SessionFactory.class)
            .getStatistics();
    statistics.setStatisticsEnabled(true);

    messageRepository.findByConversation_Id(1L);

    long entityFetchCount = statistics.getEntityFetchCount();
    assertThat(entityFetchCount).isEqualTo(1);
  }
}
