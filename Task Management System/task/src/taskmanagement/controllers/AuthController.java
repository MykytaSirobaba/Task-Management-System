package taskmanagement.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dtos.user.UserAuthDtoRequest;
import taskmanagement.services.AuthService;

import java.util.Map;

/**
 * Created by Mykyta Sirobaba on 20.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> register(@Valid @RequestBody UserAuthDtoRequest userAuthDtoRequest) {
        return authService.register(userAuthDtoRequest);
    }

    @PostMapping("/auth/token")
    public ResponseEntity<?> authenticate(Authentication authentication) {
        try {
            return ResponseEntity.ok(authService.getTokens(authentication));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
