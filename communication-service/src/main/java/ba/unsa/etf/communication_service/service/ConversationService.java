package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.mapper.ConversationMapper;
import ba.unsa.etf.communication_service.repository.ConversationRepository;
import ba.unsa.etf.communication_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository;
  private final UserRepository userRepository;
  private final ConversationMapper conversationMapper;

  @Transactional(readOnly = true)
  public Page<ConversationDTO> findAll(Pageable pageable) {
    return conversationRepository.findAll(pageable).map(conversationMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Optional<ConversationDTO> findById(Long id) {
    return conversationRepository.findById(id).map(conversationMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Page<ConversationDTO> findByName(String name, Pageable pageable) {
    return conversationRepository.findByName(name, pageable).map(conversationMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Page<ConversationDTO> findByUserId(Long userId, Pageable pageable) {
    return conversationRepository.findByUser_Id(userId, pageable).map(conversationMapper::toDTO);
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
