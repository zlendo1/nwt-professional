package ba.unsa.etf.user_management_service.user.controller;

import ba.unsa.etf.user_management_service.user.dto.UserDTO;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.repository.UserRepository;
import ba.unsa.etf.user_management_service.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                userRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("User with id: " + id + " not found"))
        );
    }
    @GetMapping("uuid/{uuid}")
    public ResponseEntity<User> getUser(@PathVariable String uuid) {
        return ResponseEntity.ok(
                userRepository.findByUuid(uuid)
                        .orElseThrow(() -> new NoSuchElementException("User with uuid: \"" + uuid + "\" not found"))
        );
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with id: " + id + " not found");
        }
        user.setId(id);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User with id: " + id + " has been deleted successfully.");
    }

}
