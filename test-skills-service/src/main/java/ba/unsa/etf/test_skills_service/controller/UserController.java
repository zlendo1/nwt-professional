package ba.unsa.etf.test_skills_service.controller;

import ba.unsa.etf.test_skills_service.dto.user.CreateUserRequest;
import ba.unsa.etf.test_skills_service.dto.user.UpdateUserRequest;
import ba.unsa.etf.test_skills_service.dto.user.UserDto;
import ba.unsa.etf.test_skills_service.service.UserService;
import jakarta.validation.Valid; // Import za @Valid
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserDto> getAllUsers() { // Vraća listu DTOs
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long id) { // Vraća DTO
    return userService
        .getUserById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found")); // Bacanje izuzetka za 404
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(
      @Valid @RequestBody
          CreateUserRequest request) { // Prima Request DTO, @Valid aktivira validaciju
    try {
      UserDto createdUser = userService.createUser(request);
      // TODO: Consider returning Location header
      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    } catch (Exception e) { // Uhvati potencijalne greške (npr. unique constraint violation)
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Could not create user: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
      @PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) { // Prima Request DTO
    return userService
        .updateUser(id, request)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    if (userService.deleteUser(id)) {
      return ResponseEntity.noContent().build();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
  }
}
