package ba.unsa.etf.user_management_service.event.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ba.unsa.etf.user_management_service.event.model.UserEvent;
import ba.unsa.etf.user_management_service.user.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

@ExtendWith(MockitoExtension.class)
class UserEventPublisherTest {

  @Mock private StreamBridge streamBridge;

  @InjectMocks private UserEventPublisher userEventPublisher;

  private User testUser;
  private static final String USER_EVENTS_BINDING = "userEvents-out-0";

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setId(1L);
    testUser.setEmail("test@example.com");
    testUser.setFirstName("John");
    testUser.setLastName("Doe");
  }

  @Test
  void publishUserCreatedEvent_ShouldPublishEventSuccessfully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserCreatedEvent(testUser);

    ArgumentCaptor<UserEvent> eventCaptor = ArgumentCaptor.forClass(UserEvent.class);
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), eventCaptor.capture());

    UserEvent capturedEvent = eventCaptor.getValue();
    assertEquals("CREATE", capturedEvent.getEventType());
    assertEquals(testUser, capturedEvent.getUser());
    assertEquals("user-management-service", capturedEvent.getSource());
    assertNotNull(capturedEvent.getEventId());
    assertNotNull(capturedEvent.getTimestamp());
    assertTrue(capturedEvent.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
  }

  @Test
  void publishUserUpdatedEvent_ShouldPublishEventSuccessfully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserUpdatedEvent(testUser);

    ArgumentCaptor<UserEvent> eventCaptor = ArgumentCaptor.forClass(UserEvent.class);
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), eventCaptor.capture());

    UserEvent capturedEvent = eventCaptor.getValue();
    assertEquals("UPDATE", capturedEvent.getEventType());
    assertEquals(testUser, capturedEvent.getUser());
    assertEquals("user-management-service", capturedEvent.getSource());
    assertNotNull(capturedEvent.getEventId());
    assertNotNull(capturedEvent.getTimestamp());
  }

  @Test
  void publishUserDeletedEvent_ShouldPublishEventSuccessfully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserDeletedEvent(testUser);

    ArgumentCaptor<UserEvent> eventCaptor = ArgumentCaptor.forClass(UserEvent.class);
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), eventCaptor.capture());

    UserEvent capturedEvent = eventCaptor.getValue();
    assertEquals("DELETE", capturedEvent.getEventType());
    assertEquals(testUser, capturedEvent.getUser());
    assertEquals("user-management-service", capturedEvent.getSource());
    assertNotNull(capturedEvent.getEventId());
    assertNotNull(capturedEvent.getTimestamp());
  }

  @Test
  void publishUserCreatedEvent_ShouldHandleExceptionGracefully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class)))
        .thenThrow(new RuntimeException("Connection failed"));

    assertDoesNotThrow(() -> userEventPublisher.publishUserCreatedEvent(testUser));
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), any(UserEvent.class));
  }

  @Test
  void publishUserUpdatedEvent_ShouldHandleExceptionGracefully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class)))
        .thenThrow(new RuntimeException("Serialization failed"));

    assertDoesNotThrow(() -> userEventPublisher.publishUserUpdatedEvent(testUser));
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), any(UserEvent.class));
  }

  @Test
  void publishUserDeletedEvent_ShouldHandleExceptionGracefully() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class)))
        .thenThrow(new IllegalArgumentException("Invalid binding"));

    assertDoesNotThrow(() -> userEventPublisher.publishUserDeletedEvent(testUser));
    verify(streamBridge).send(eq(USER_EVENTS_BINDING), any(UserEvent.class));
  }

  @Test
  void createEvent_ShouldGenerateUniqueEventIds() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserCreatedEvent(testUser);
    userEventPublisher.publishUserUpdatedEvent(testUser);

    ArgumentCaptor<UserEvent> eventCaptor = ArgumentCaptor.forClass(UserEvent.class);
    verify(streamBridge, times(2)).send(eq(USER_EVENTS_BINDING), eventCaptor.capture());

    var events = eventCaptor.getAllValues();
    assertNotEquals(events.get(0).getEventId(), events.get(1).getEventId());
  }

  @Test
  void createEvent_ShouldGenerateTimestampsInOrder() throws InterruptedException {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserCreatedEvent(testUser);
    Thread.sleep(1);
    userEventPublisher.publishUserUpdatedEvent(testUser);

    ArgumentCaptor<UserEvent> eventCaptor = ArgumentCaptor.forClass(UserEvent.class);
    verify(streamBridge, times(2)).send(eq(USER_EVENTS_BINDING), eventCaptor.capture());

    var events = eventCaptor.getAllValues();
    assertTrue(
        events.get(0).getTimestamp().isBefore(events.get(1).getTimestamp())
            || events.get(0).getTimestamp().isEqual(events.get(1).getTimestamp()));
  }

  @Test
  void publishEvents_ShouldUseCorrectBinding() {
    when(streamBridge.send(eq(USER_EVENTS_BINDING), any(UserEvent.class))).thenReturn(true);

    userEventPublisher.publishUserCreatedEvent(testUser);
    userEventPublisher.publishUserUpdatedEvent(testUser);
    userEventPublisher.publishUserDeletedEvent(testUser);

    verify(streamBridge, times(3)).send(eq(USER_EVENTS_BINDING), any(UserEvent.class));
  }
}
