package ba.unsa.etf.communication_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ba.unsa.etf.communication_service.dto.user.CreateUserDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private UserService userService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void testGetAll() throws Exception {
    List<UserDTO> users =
        Collections.singletonList(new UserDTO(1L, "testuser", "testuser@example.com"));
    given(userService.findAll()).willReturn(users);

    mockMvc
        .perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(users)));
  }

  @Test
  public void testGetById() throws Exception {
    UserDTO user = new UserDTO(1L, "testuser", "testuser@example.com");
    given(userService.findById(1L)).willReturn(Optional.of(user));

    mockMvc
        .perform(get("/api/users?id=1"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }

  @Test
  public void testGetById_NotFound() throws Exception {
    given(userService.findById(1L)).willReturn(Optional.empty());

    mockMvc.perform(get("/api/users?id=1")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetByUsername() throws Exception {
    UserDTO user = new UserDTO(1L, "testuser", "testuser@example.com");
    given(userService.findByUsername("testuser")).willReturn(Optional.of(user));

    mockMvc
        .perform(get("/api/users?username=testuser"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }

  @Test
  public void testGetByUsername_NotFound() throws Exception {
    given(userService.findByUsername("testuser")).willReturn(Optional.empty());

    mockMvc.perform(get("/api/users?username=testuser")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetByEmail() throws Exception {
    UserDTO user = new UserDTO(1L, "testuser", "testuser@example.com");
    given(userService.findByEmail("testuser@example.com")).willReturn(Optional.of(user));

    mockMvc
        .perform(get("/api/users?email=testuser@example.com"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }

  @Test
  public void testGetByEmail_NotFound() throws Exception {
    given(userService.findByEmail("testuser@example.com")).willReturn(Optional.empty());

    mockMvc.perform(get("/api/users?email=testuser@example.com")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetByConversationId() throws Exception {
    List<UserDTO> users =
        Collections.singletonList(new UserDTO(1L, "testuser", "testuser@example.com"));
    given(userService.findByConversationId(1L)).willReturn(users);

    mockMvc
        .perform(get("/api/users?conversationId=1"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(users)));
  }

  @Test
  public void testLinkWithConversation() throws Exception {
    doNothing().when(userService).linkWithConversation(1L, 1L);

    mockMvc.perform(put("/api/users/1/link/conversation/1")).andExpect(status().isOk());
  }

  @Test
  public void testLinkWithConversation_NotFound() throws Exception {
    doThrow(new EntityNotFoundException("User not found"))
        .when(userService)
        .linkWithConversation(1L, 1L);

    mockMvc
        .perform(put("/api/users/1/link/conversation/1"))
        .andExpect(status().isNotFound())
        .andExpect(header().string("error", "User not found"));
  }

  @Test
  public void testUnlinkWithConversation() throws Exception {
    doNothing().when(userService).unlinkWithConversation(1L, 1L);

    mockMvc.perform(put("/api/users/1/unlink/conversation/1")).andExpect(status().isOk());
  }

  @Test
  public void testUnlinkWithConversation_NotFound() throws Exception {
    doThrow(new EntityNotFoundException("User not found"))
        .when(userService)
        .unlinkWithConversation(1L, 1L);

    mockMvc
        .perform(put("/api/users/1/unlink/conversation/1"))
        .andExpect(status().isNotFound())
        .andExpect(header().string("error", "User not found"));
  }

  @Test
  public void testCreate() throws Exception {
    CreateUserDTO createUserDTO = new CreateUserDTO("testuser", "testuser@example.com");
    UserDTO user = new UserDTO(1L, "testuser", "testuser@example.com");
    given(userService.create(any(CreateUserDTO.class))).willReturn(user);

    mockMvc
        .perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }

  @Test
  public void testUpdate() throws Exception {
    CreateUserDTO createUserDTO = new CreateUserDTO("updateduser", "updateduser@example.com");
    UserDTO user = new UserDTO(1L, "updateduser", "updateduser@example.com");
    given(userService.update(eq(1L), any(CreateUserDTO.class))).willReturn(Optional.of(user));

    mockMvc
        .perform(
            put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }

  @Test
  public void testUpdate_NotFound() throws Exception {
    CreateUserDTO createUserDTO = new CreateUserDTO("updateduser", "updateduser@example.com");
    given(userService.update(eq(1L), any(CreateUserDTO.class))).willReturn(Optional.empty());

    mockMvc
        .perform(
            put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDelete() throws Exception {
    given(userService.delete(1L)).willReturn(true);

    mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
  }

  @Test
  public void testDelete_NotFound() throws Exception {
    given(userService.delete(1L)).willReturn(false);

    mockMvc.perform(delete("/api/users/1")).andExpect(status().isNotFound());
  }
}
