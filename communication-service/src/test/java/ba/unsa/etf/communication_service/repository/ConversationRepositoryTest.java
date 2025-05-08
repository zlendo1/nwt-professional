package ba.unsa.etf.communication_service.repository;

import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.entity.User;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ConversationRepositoryTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    @Sql("/test-data.sql")
    public void setUp() {
        // Load test data from SQL script
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
    }

    @Test
    public void testFindByName_NoNPlusOneProblem() {
        Statistics statistics = entityManager.getEntityManagerFactory().unwrap(org.hibernate.SessionFactory.class).getStatistics();
        statistics.setStatisticsEnabled(true);

        conversationRepository.findByName("Test Conversation 1");

        long entityFetchCount = statistics.getEntityFetchCount();
        assertThat(entityFetchCount).isEqualTo(1);
    }
}
