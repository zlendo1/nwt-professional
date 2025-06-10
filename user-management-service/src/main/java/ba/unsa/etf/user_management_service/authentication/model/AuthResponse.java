package ba.unsa.etf.user_management_service.authentication.model;

import ba.unsa.etf.user_management_service.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private User user;
}
