package ba.unsa.etf.user_management_service.user.service;

import ba.unsa.etf.user_management_service.user.dto.UserDTO;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> getUserByUuid(String uuid){ return userRepository.findByUuid(uuid);}
    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(UserDTO signUpRequest) {
        // 1. Check if user with this email already exists
        userRepository.findByEmail(signUpRequest.getEmail()).ifPresent(user -> {
            throw new IllegalStateException("User with email " + signUpRequest.getEmail() + " already exists.");
        });

        // 2. Create a new user and map data from DTO
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());

        // 3. Hash the password
        user.setPasswordHashed(passwordEncoder.encode(signUpRequest.getPassword()));

        // 4. Set a default role
        user.setRole("ROLE_USER");

        // The UUID is generated automatically by @PrePersist

        // 5. Save the user
        return userRepository.save(user);
    }
}
