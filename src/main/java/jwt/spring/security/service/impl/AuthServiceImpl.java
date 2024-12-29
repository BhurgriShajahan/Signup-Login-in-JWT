package jwt.spring.security.service.impl;

import jwt.spring.security.config.JwtUtil;
import jwt.spring.security.enums.RoleType;
import jwt.spring.security.exceptions.CustomException;
import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.UserSignupDto;
import jwt.spring.security.model.entities.Role;
import jwt.spring.security.model.entities.User;
import jwt.spring.security.model.mapper.UserSignupMapper;
import jwt.spring.security.model.request.LoginRequest;
import jwt.spring.security.model.request.SignupRequest;
import jwt.spring.security.repository.RoleRepository;
import jwt.spring.security.repository.UserRepository;
import jwt.spring.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private UserSignupMapper userSignupMapper;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserSignupMapper userSignupMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userSignupMapper = userSignupMapper;
    }

    @Override
    public CustomResponseEntity<?> signup(SignupRequest request) {
        try {
            if (userRepository.existsByUsername(request.getUsername())) {
                return new CustomResponseEntity<>(1000, "Username is already registered.");
            } else if (userRepository.existsByEmail(request.getEmail())) {
                return new CustomResponseEntity<>(1001, "Email is already in registered.");
            } else if (userRepository.existsByPhone(request.getPhone())) {
                return new CustomResponseEntity<>(1002, "Phone is already in registered.");
            }
            UserSignupDto userSignupDto = new UserSignupDto();
            logger.error("Username can't be null or empty!");
            userSignupDto.setUsername(request.getUsername());
            userSignupDto.setEmail(request.getEmail());
            userSignupDto.setPhone(request.getPhone());
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            userSignupDto.setPassword(encodedPassword);

            User user = userSignupMapper.dtoToEntity(userSignupDto);

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new CustomException("Role not found."));
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("username", user.getUsername());
            responseData.put("email", user.getEmail());
            responseData.put("roles", roles.stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

            return new CustomResponseEntity<>(responseData, "User registered successfully.");

        } catch (CustomException e) {
            return new CustomResponseEntity<>(1000, e.getMessage(), 400);
        } catch (Exception e) {
            logger.error("Unexpected error during signup", e);
            return new CustomResponseEntity<>(1000, "An unexpected error occurred.", 500);
        }
    }

    @Override
    public CustomResponseEntity<?> login(LoginRequest request) {
        try {

            User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(),request.getUsernameOrEmail());

            if (user == null) {
                logger.error("User not found for username or email: " + request.getUsernameOrEmail());
                return new CustomResponseEntity<>(1001, "Invalid username or password.");
            }
            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.error("Password mismatch for user: " + request.getUsernameOrEmail());
                return new CustomResponseEntity<>(1001, "Invalid username/email or password.");
            }

            List<String> rolesList = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toList());

            Set<GrantedAuthority> authorities = rolesList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            String token = jwtUtil.generateToken(user.getUsername(), authorities);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("username", user.getUsername());
            responseData.put("token", token);
            responseData.put("roles", rolesList);

            return new CustomResponseEntity<>(responseData, "Login successfully.");
        } catch (Exception exception) {
            logger.error("Error occurred during login", exception);
            return CustomResponseEntity.errorResponse(exception);
        }
    }
}
