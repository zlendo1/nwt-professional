package ba.unsa.etf.user_management_service.authentication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class LoginRequest extends AuthRequest {

    public LoginRequest(String email, String password) {
        super(email, password);
    }
}
