package jwt.spring.security.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username or emails is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters.")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public @NotBlank(message = "Password is required!") @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required!") @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters.") String password) {
        this.password = password;
    }
}

