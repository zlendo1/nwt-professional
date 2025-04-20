package ba.unsa.etf.communication_service.service;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.user.CreateUserDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.mapper.ConversationMapper;
import ba.unsa.etf.communication_service.mapper.UserMapper;
import ba.unsa.etf.communication_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final ConversationMapper conversationMapper;

  @Transactional(readOnly = true)
  public List<UserDTO> findAll() {
    return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findById(Long id) {
    return userRepository.findById(id).map(userMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findByUsername(String username) {
    return userRepository.findByUsername(username).map(userMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Optional<UserDTO> findByEmail(String username) {
    return userRepository.findByEmail(username).map(userMapper::toDTO);
  }

  @Transactional(readOnly = true)
  public Optional<List<ConversationDTO>> findConversationsById(Long id) {
    return userRepository
        .findById(id)
        .map(
            user ->
                user.getConversations().stream()
                    .map(conversationMapper::toDTO)
                    .collect(Collectors.toList()));
  }

  @Transactional
  public UserDTO create(CreateUserDTO dto) {
    User user = userRepository.save(userMapper.fromCreateDTO(dto));

    return userMapper.toDTO(user);
  }

  @Transactional
  public Optional<UserDTO> update(Long id, CreateUserDTO dto) {
    return userRepository
        .findById(id)
        .map(
            user -> {
              user.setUsername(dto.getUsername());
              user.setEmail(dto.getEmail());

              userRepository.save(user);

              return userMapper.toDTO(user);
            });
  }

  @Transactional
  public boolean delete(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);

      return true;
    }

    return false;
  }
}
