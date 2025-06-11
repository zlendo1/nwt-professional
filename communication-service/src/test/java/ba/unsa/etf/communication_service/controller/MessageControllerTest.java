package ba.unsa.etf.communication_service.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
public class MessageControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private MessageService messageService;

  @Autowired private ObjectMapper objectMapper;

  private ResultMatcher expectContentExact(String expected) {
    return result -> {
      String actualJson = result.getResponse().getContentAsString();
      JsonNode actualContent = objectMapper.readTree(actualJson).get("content");

      JSONAssert.assertEquals(expected, actualContent.toString(), JSONCompareMode.STRICT);
    };
  }

  @Test
  public void testGetAllMessages() throws Exception {
    MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
    List<MessageDTO> messages = Collections.singletonList(messageDTO);
    Page<MessageDTO> messagesPage = new PageImpl<>(messages);

    Mockito.when(messageService.findAll(any())).thenReturn(messagesPage);

    mockMvc
        .perform(get("/api/message"))
        .andExpect(status().isOk())
        .andExpect(expectContentExact(objectMapper.writeValueAsString(messages)));
  }

  @Test
  public void testGetMessageById() throws Exception {
    MessageDTO message = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);

    Mockito.when(messageService.findById(anyLong())).thenReturn(Optional.of(message));

    mockMvc
        .perform(get("/api/message?id=1"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(message)));
  }

  @Test
  public void testGetMessagesByConversationId() throws Exception {
    MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
    List<MessageDTO> messages = Collections.singletonList(messageDTO);
    Page<MessageDTO> messagesPage = new PageImpl<>(messages);

    Mockito.when(messageService.findByConversationId(anyLong(), any())).thenReturn(messagesPage);

    mockMvc
        .perform(get("/api/message?conversationId=1"))
        .andExpect(status().isOk())
        .andExpect(expectContentExact(objectMapper.writeValueAsString(messages)));
  }

  @Test
  public void testGetMessagesByUserAndConversationId() throws Exception {
    MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
    List<MessageDTO> messages = Collections.singletonList(messageDTO);
    Page<MessageDTO> messagesPage = new PageImpl<>(messages);

    Mockito.when(messageService.findByUserAndConversationId(anyLong(), anyLong(), any()))
        .thenReturn(messagesPage);

    mockMvc
        .perform(get("/api/message?userId=1&conversationId=1"))
        .andExpect(status().isOk())
        .andExpect(expectContentExact(objectMapper.writeValueAsString(messages)));
  }

  @Test
  public void testCreateMessage() throws Exception {
    CreateMessageDTO createMessageDTO = new CreateMessageDTO(1L, 1L, "Hello");
    MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);

    Mockito.when(messageService.create(any(CreateMessageDTO.class))).thenReturn(messageDTO);

    mockMvc
        .perform(
            post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createMessageDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(messageDTO.getId().intValue())))
        .andExpect(jsonPath("$.userId", is(messageDTO.getUserId().intValue())))
        .andExpect(jsonPath("$.username", is(messageDTO.getUsername())))
        .andExpect(jsonPath("$.conversationId", is(messageDTO.getConversationId().intValue())))
        .andExpect(jsonPath("$.content", is(messageDTO.getContent())));
  }

  @Test
  public void testDeleteMessage() throws Exception {
    Mockito.when(messageService.delete(anyLong())).thenReturn(true);

    mockMvc.perform(delete("/api/message/1")).andExpect(status().isNoContent());
  }
}
