package taskmanagement.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import taskmanagement.dtos.user.UserAuthDtoRequest;
import taskmanagement.entities.User;

import java.util.Map;

/**
 * Created by Mykyta Sirobaba on 23.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Service
public class AuthService {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> register(@RequestBody UserAuthDtoRequest userAuthDtoRequest) {
        if (userAuthDtoRequest.getEmail() == null || userAuthDtoRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }
        if (userAuthDtoRequest.getEmail().isEmpty() || userAuthDtoRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }
        if (!userAuthDtoRequest.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        if (userAuthDtoRequest.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }
        if (userService.findByEmail(userAuthDtoRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        }
        User user = userService.create(userAuthDtoRequest);
        return ResponseEntity.ok(user);
    }

    public Map<String, String> getTokens(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NullPointerException();
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = tokenService.createToken(user);
        return Map.of("token", token);
    }
}
