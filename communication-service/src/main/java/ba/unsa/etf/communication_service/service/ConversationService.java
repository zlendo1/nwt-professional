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
  public Page<ConversationDTO> findByUserId(Long userId, Pageable pageable) {
    return conversationRepository.findByUser_Id(userId, pageable).map(conversationMapper::toDTO);
  }

  @Transactional
  public ConversationDTO create(CreateConversationDTO dto) {
    Conversation conversation = conversationMapper.fromCreateDTO(dto);

    User user1 =
        userRepository
            .findById(dto.getUser1_id())
            .orElseThrow(() -> new EntityNotFoundException("User1 not found"));

    User user2 =
        userRepository
            .findById(dto.getUser2_id())
            .orElseThrow(() -> new EntityNotFoundException("User2 not found"));

    conversation.setUser1(user1);
    conversation.setUser2(user2);

    return conversationMapper.toDTO(conversationRepository.save(conversation));
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
