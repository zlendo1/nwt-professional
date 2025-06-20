package ba.unsa.etf.user_management_service.authentication.service;

import ba.unsa.etf.user_management_service.authentication.model.AuthResponse;
import ba.unsa.etf.user_management_service.authentication.model.LoginRequest;
import ba.unsa.etf.user_management_service.authentication.model.RegisterRequest;
import ba.unsa.etf.user_management_service.user.model.User;
import ba.unsa.etf.user_management_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
  private final JwtService jwtService;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthResponse register(RegisterRequest registerRequest) {
    var email = registerRequest.getEmail();
    var passwordHashed = passwordEncoder.encode(registerRequest.getPassword());
    String profilePictureUrl = registerRequest.getProfilePicture();
    if (profilePictureUrl == null || profilePictureUrl.isEmpty()) {
      profilePictureUrl =
          "https://png.pngtree.com/png-vector/20190909/ourmid/pngtree-outline-user-icon-png-image_1727916.jpg";
    }
    var user =
        User.builder()
            .email(email)
            .passwordHashed(passwordHashed)
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .dateOfBirth(registerRequest.getDateOfBirth())
            .profilePicture(profilePictureUrl)
            .build();
    userService.createUser(user);

    return login(new LoginRequest(email, registerRequest.getPassword()));
  }

  public AuthResponse login(LoginRequest loginRequest) {
    var user =
        userService
            .getUserByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHashed())) {
      throw new IllegalArgumentException("Invalid password");
    }

    String accessToken = jwtService.generate(loginRequest.getEmail(), "ACCESS");
    String refreshToken = jwtService.generate(loginRequest.getEmail(), "REFRESH");

    return new AuthResponse(accessToken, refreshToken, user);
  }
}

