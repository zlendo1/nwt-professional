package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.mapper.ConversationMapper;
import ba.unsa.etf.communication_service.mapper.UserMapper;
import ba.unsa.etf.communication_service.repository.ConversationRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository;
  private final ConversationMapper conversationMapper;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public List<ConversationDTO> findAll() {
    return conversationRepository.findAll().stream()
        .map(conversationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<ConversationDTO> findById(Long id) {
    return conversationRepository.findById(id).map(conversationMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Set<ConversationDTO> findByName(String name) {
    return conversationRepository.findByName(name).stream()
        .map(conversationMapper::toDTO)
        .collect(Collectors.toSet());
  }

  @Transactional(readOnly = true)
  public Optional<Set<UserDTO>> findConversationById(Long id) {
    return conversationRepository
        .findById(id)
        .map(
            conversation ->
                conversation.getUsers().stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toSet()));
  }

  @Transactional
  public ConversationDTO create(CreateConversationDTO dto) {
    Conversation conversation = conversationRepository.save(conversationMapper.fromCreateDTO(dto));

    return conversationMapper.toDTO(conversation);
  }

  @Transactional
  public boolean delete(Long id) {
    if (conversationRepository.existsById(id)) {
      conversationRepository.deleteById(id);

      return true;
    }

    return false;
  }
}
