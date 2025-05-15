package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.mapper.ConversationMapper;
import ba.unsa.etf.communication_service.mapper.UserMapper;
import ba.unsa.etf.communication_service.repository.ConversationRepository;
import ba.unsa.etf.communication_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository;
  private final UserRepository userRepository;
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
  public List<ConversationDTO> findByName(String name) {
    return conversationRepository.findByName(name).stream()
        .map(conversationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<List<UserDTO>> findUsersById(Long id) {
    return conversationRepository
        .findById(id)
        .map(
            conversation ->
                conversation.getUsers().stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList()));
  }

  @Transactional
  public void linkWithUser(Long conversationId, Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Conversation conversation =
        conversationRepository
            .findById(conversationId)
            .orElseThrow(() -> new EntityNotFoundException("Conversation not found"));

    user.getConversations().add(conversation);
    conversation.getUsers().add(user);

    userRepository.save(user);
    conversationRepository.save(conversation);
  }

  @Transactional
  public void unlinkWithUser(Long conversationId, Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Conversation conversation =
        conversationRepository
            .findById(conversationId)
            .orElseThrow(() -> new EntityNotFoundException("Conversation not found"));

    user.getConversations().remove(conversation);
    conversation.getUsers().remove(user);

    userRepository.save(user);
    conversationRepository.save(conversation);
  }

  @Transactional
  public ConversationDTO create(CreateConversationDTO dto) {
    Conversation conversation = conversationRepository.save(conversationMapper.fromCreateDTO(dto));

    return conversationMapper.toDTO(conversation);
  }

  @Transactional
  public Optional<ConversationDTO> update(Long id, CreateConversationDTO dto) {
    return conversationRepository
        .findById(id)
        .map(
            conversation -> {
              conversation.setName(dto.getName());

              conversationRepository.save(conversation);

              return conversationMapper.toDTO(conversation);
            });
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
