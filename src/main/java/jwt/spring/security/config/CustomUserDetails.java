package jwt.spring.security.config;

import jwt.spring.security.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String username;
    private String password;

    // Constructor to map the User entity
    public CustomUserDetails(User user) {
        this.userId = user.getId(); // Assuming User entity has an 'id' field
        this.username = user.getEmail();
        this.password = user.getPassword();

    }

    // Getter for userId
    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Update this if roles/authorities are used in your app
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
