package ba.unsa.etf.communication_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private ConversationService conversationService;

  @Autowired private ObjectMapper objectMapper;

  private ResultMatcher expectContentExact(String expected) {
    return result -> {
      String actualJson = result.getResponse().getContentAsString();
      JsonNode actualContent = objectMapper.readTree(actualJson).get("content");

      JSONAssert.assertEquals(expected, actualContent.toString(), JSONCompareMode.STRICT);
    };
  }

  @Test
  public void testGetAll() throws Exception {
    List<ConversationDTO> conversations =
        Collections.singletonList(
            new ConversationDTO(1L, 1L, "user1@email.com", 2L, "user2@email.com"));
    Page<ConversationDTO> conversationsPage = new PageImpl<>(conversations);

    given(conversationService.findAll(any())).willReturn(conversationsPage);

    mockMvc
        .perform(get("/api/conversation"))
        .andExpect(status().isOk())
        .andExpect(expectContentExact(objectMapper.writeValueAsString(conversations)));
  }

  @Test
  public void testGetById() throws Exception {
    ConversationDTO conversation =
        new ConversationDTO(1L, 1L, "user1@email.com", 2L, "user2@email.com");

    given(conversationService.findById(1L)).willReturn(Optional.of(conversation));

    mockMvc
        .perform(get("/api/conversation/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(conversation)));
  }

  @Test
  public void testGetById_NotFound() throws Exception {
    given(conversationService.findById(1L)).willReturn(Optional.empty());

    mockMvc.perform(get("/api/conversation/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetUsers() throws Exception {
    List<ConversationDTO> conversations =
        Collections.singletonList(
            new ConversationDTO(1L, 1L, "user1@email.com", 2L, "user2@email.com"));
    Page<ConversationDTO> conversationsPage = new PageImpl<>(conversations);

    given(conversationService.findByUserId(eq(1L), any())).willReturn(conversationsPage);

    mockMvc
        .perform(get("/api/conversation/userId/1"))
        .andExpect(status().isOk())
        .andExpect(expectContentExact(objectMapper.writeValueAsString(conversations)));
  }

  @Test
  public void testCreate() throws Exception {
    CreateConversationDTO createConversationDTO = new CreateConversationDTO(1L, 2L);
    ConversationDTO conversation =
        new ConversationDTO(1L, 1L, "user1@email.com", 2L, "user2@email.com");

    given(conversationService.create(any(CreateConversationDTO.class))).willReturn(conversation);

    mockMvc
        .perform(
            post("/api/conversation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createConversationDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(conversation)));
  }

  @Test
  public void testDelete() throws Exception {
    given(conversationService.delete(1L)).willReturn(true);

    mockMvc.perform(delete("/api/conversation/1")).andExpect(status().isNoContent());
  }

  @Test
  public void testDelete_NotFound() throws Exception {
    given(conversationService.delete(1L)).willReturn(false);

    mockMvc.perform(delete("/api/conversation/1")).andExpect(status().isNotFound());
  }
}
