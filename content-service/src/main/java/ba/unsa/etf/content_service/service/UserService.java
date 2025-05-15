package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.dto.UserDto;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.mapper.UserMapper;
import ba.unsa.etf.content_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Transactional(readOnly = true)
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(user -> userMapper.toDto(user)) // Ispravno mapiranje entiteta u DTO
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserById(Long id) {
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      return Optional.of(userMapper.toDto(userOpt.get()));
    }
    return Optional.empty();
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserByUsername(String username) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    if (userOpt.isPresent()) {
      return Optional.of(userMapper.toDto(userOpt.get()));
    }
    return Optional.empty();
  }

  @Transactional
  public UserDto createUser(UserDto userDto) {
    // Lozinka nije uključena u Dto, možeš je postaviti unutar entiteta
    User user = userMapper.toEntity(userDto);
    // Ovdje trebaš postaviti lozinku direktno u entitet, npr. hashiranje lozinke
    // user.setPassword(hashPassword(userDto.getPassword()));

    User savedUser = userRepository.save(user);
    return userMapper.toDto(savedUser);
  }

  @Transactional
  public UserDto updateUser(Long id, UserDto userDto) {
    Optional<User> existingUser = userRepository.findById(id);
    if (existingUser.isPresent()) {
      User user = existingUser.get();
      // Update user details
      user.setUsername(userDto.getUsername());
      user.setUserlastname(userDto.getUserlastname());
      user.setEmail(userDto.getEmail());
      user.setRegdate(userDto.getRegdate());
      // Lozinka se ne ažurira iz dto-a

      User updatedUser = userRepository.save(user);
      return userMapper.toDto(updatedUser);
    }
    return null; // Return null if user not found
  }

  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
