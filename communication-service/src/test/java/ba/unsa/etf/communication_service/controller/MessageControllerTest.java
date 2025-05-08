package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testGetAllMessages() throws Exception {
        MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
        List<MessageDTO> messages = Collections.singletonList(messageDTO);

        Mockito.when(messageService.findAll()).thenReturn(messages);

        mockMvc.perform(get("/api/message"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(messageDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(messageDTO.getUserId().intValue())))
                .andExpect(jsonPath("$[0].username", is(messageDTO.getUsername())))
                .andExpect(jsonPath("$[0].conversationId", is(messageDTO.getConversationId().intValue())))
                .andExpect(jsonPath("$[0].content", is(messageDTO.getContent())));
    }

    @Test
    public void testGetMessageById() throws Exception {
        MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);

        Mockito.when(messageService.findById(anyLong())).thenReturn(Optional.of(messageDTO));

        mockMvc.perform(get("/api/message?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(messageDTO.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(messageDTO.getUserId().intValue())))
                .andExpect(jsonPath("$.username", is(messageDTO.getUsername())))
                .andExpect(jsonPath("$.conversationId", is(messageDTO.getConversationId().intValue())))
                .andExpect(jsonPath("$.content", is(messageDTO.getContent())));
    }

    @Test
    public void testGetMessagesByConversationId() throws Exception {
        MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
        List<MessageDTO> messages = Collections.singletonList(messageDTO);

        Mockito.when(messageService.findByConversationId(anyLong())).thenReturn(messages);

        mockMvc.perform(get("/api/message?conversationId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(messageDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(messageDTO.getUserId().intValue())))
                .andExpect(jsonPath("$[0].username", is(messageDTO.getUsername())))
                .andExpect(jsonPath("$[0].conversationId", is(messageDTO.getConversationId().intValue())))
                .andExpect(jsonPath("$[0].content", is(messageDTO.getContent())));
    }

    @Test
    public void testGetMessagesByUserAndConversationId() throws Exception {
        MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);
        List<MessageDTO> messages = Collections.singletonList(messageDTO);

        Mockito.when(messageService.findByUserAndConversationId(anyLong(), anyLong())).thenReturn(messages);

        mockMvc.perform(get("/api/message?userId=1&conversationId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(messageDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(messageDTO.getUserId().intValue())))
                .andExpect(jsonPath("$[0].username", is(messageDTO.getUsername())))
                .andExpect(jsonPath("$[0].conversationId", is(messageDTO.getConversationId().intValue())))
                .andExpect(jsonPath("$[0].content", is(messageDTO.getContent())));
    }

    @Test
    public void testCreateMessage() throws Exception {
        CreateMessageDTO createMessageDTO = new CreateMessageDTO(1L, 1L, "Hello");
        MessageDTO messageDTO = new MessageDTO(1L, 1L, "user1", 1L, "Hello", null);

        Mockito.when(messageService.create(any(CreateMessageDTO.class))).thenReturn(messageDTO);

        mockMvc.perform(post("/api/message")
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

        mockMvc.perform(delete("/api/message/1"))
                .andExpect(status().isNoContent());
    }
}
