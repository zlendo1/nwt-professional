package ba.unsa.etf.communication_service.event.listener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.event.model.UserEvent;
import ba.unsa.etf.communication_service.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserEventListenerTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserEventListener userEventListener;

  private UserEvent userEvent;
  private User user;

  @BeforeEach
  void setUp() {
    userEvent = new UserEvent();
    userEvent.setId(1L);
    userEvent.setEmail("test@example.com");
    userEvent.setEventType("CREATE");
    userEvent.setEventId("event-123");
    userEvent.setTimestamp(LocalDateTime.now());
    userEvent.setSource("user-service");

    user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
  }

  @Test
  void userEvents_CreateEvent_ShouldSaveNewUser() {
    when(userRepository.save(any(User.class))).thenReturn(user);
    userEventListener.userEvents().accept(userEvent);
    verify(userRepository)
        .save(argThat(savedUser -> savedUser.getEmail().equals("test@example.com")));
  }

  @Test
  void userEvents_UpdateEvent_ExistingUser_ShouldUpdateUser() {
    userEvent.setEventType("UPDATE");
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    userEventListener.userEvents().accept(userEvent);

    verify(userRepository).findByEmail("test@example.com");
    verify(userRepository)
        .save(argThat(savedUser -> savedUser.getEmail().equals("test@example.com")));
  }

  @Test
  void userEvents_UpdateEvent_NonExistentUser_ShouldNotSave() {
    userEvent.setEventType("UPDATE");
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

    userEventListener.userEvents().accept(userEvent);

    verify(userRepository).findByEmail("test@example.com");
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void userEvents_DeleteEvent_ExistingUser_ShouldDeleteUser() {
    userEvent.setEventType("DELETE");
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setEmail("test@example.com");

    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

    userEventListener.userEvents().accept(userEvent);

    verify(userRepository).findByEmail("test@example.com");
    verify(userRepository).deleteById(1L);
  }

  @Test
  void userEvents_DeleteEvent_NonExistentUser_ShouldNotDelete() {
    userEvent.setEventType("DELETE");
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

    userEventListener.userEvents().accept(userEvent);

    verify(userRepository).findByEmail("test@example.com");
    verify(userRepository, never()).deleteById(any(Long.class));
  }

  @Test
  void userEvents_UnknownEventType_ShouldDoNothing() {
    userEvent.setEventType("UNKNOWN");
    userEventListener.userEvents().accept(userEvent);
    verify(userRepository, never()).save(any(User.class));
    verify(userRepository, never()).deleteById(any(Long.class));
    verify(userRepository, never()).findByEmail(any(String.class));
  }

  @Test
  void getEventUser_WithValidEvent_ShouldReturnUser() {
    UserEvent event = new UserEvent();
    event.setId(1L);
    event.setEmail("test@example.com");
    User result = userEventListener.getEventUser(event);
    assertNotNull(result);
    assertEquals("test@example.com", result.getEmail());
  }

  @Test
  void getEventUser_WithNullValues_ShouldHandleGracefully() {
    UserEvent event = new UserEvent();
    event.setId(null);
    event.setEmail(null);
    User result = userEventListener.getEventUser(event);
    assertNotNull(result);
    assertNull(result.getEmail());
  }

  @Test
  void getEventUser_WithEmptyEvent_ShouldReturnUserWithNullFields() {
    UserEvent event = new UserEvent();
    User result = userEventListener.getEventUser(event);
    assertNotNull(result);
    assertNull(result.getEmail());
  }
}
