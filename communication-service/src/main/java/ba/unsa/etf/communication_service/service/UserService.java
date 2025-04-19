package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.mapper.ConversationMapper;
import ba.unsa.etf.communication_service.mapper.UserMapper;
import ba.unsa.etf.communication_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final ConversationMapper conversationMapper;

  @Transactional(readOnly = true)
  public List<UserDTO> findAll() {
    return userRepository.findAll().stream()
        .map(userMapper::toUserDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findById(Long id) {
    return userRepository.findById(id).map(userMapper::toUserDTO);
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findByUsername(String username) {
    return userRepository.findByUsername(username).map(userMapper::toUserDTO);
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findByEmail(String username) {
    return userRepository.findByEmail(username).map(userMapper::toUserDTO);
  }

  @Transactional(readOnly = true)
  public Optional<Set<ConversationDTO>> findConversationsById(@PathVariable Long id) {
    return userRepository
        .findById(id)
        .map(
            user ->
                user.getConversations().stream()
                    .map(conversationMapper::toConversationDTO)
                    .collect(Collectors.toSet()));
  }
}
