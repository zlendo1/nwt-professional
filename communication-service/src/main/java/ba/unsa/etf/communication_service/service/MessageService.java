package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.entity.Message;
import ba.unsa.etf.communication_service.mapper.MessageMapper;
import ba.unsa.etf.communication_service.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;

  @Transactional(readOnly = true)
  public List<MessageDTO> findAll() {
    return messageRepository.findAll().stream()
        .map(messageMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<MessageDTO> findById(Long id) {
    return messageRepository.findById(id).map(messageMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public List<MessageDTO> findByConversationId(Long conversationId) {
    return messageRepository.findByConversation_Id(conversationId).stream()
        .map(messageMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<MessageDTO> findByUserAndConversationId(Long userId, Long conversationId) {
    return messageRepository.findByUser_IdAndConversation_Id(userId, conversationId).stream()
        .map(messageMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  public MessageDTO create(CreateMessageDTO dto) {
    Message message = messageRepository.save(messageMapper.fromCreateDTO(dto));

    return messageMapper.toDTO(message);
  }

  @Transactional
  public boolean delete(Long id) {
    if (messageRepository.existsById(id)) {
      messageRepository.deleteById(id);

      return true;
    }

    return false;
  }
}
