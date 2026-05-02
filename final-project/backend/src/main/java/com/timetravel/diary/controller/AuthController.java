package com.timetravel.diary.controller;

import com.timetravel.diary.dto.AuthRequest;
import com.timetravel.diary.dto.AuthResponse;
import com.timetravel.diary.dto.RefreshTokenRequest;
import com.timetravel.diary.dto.RegisterRequest;
import com.timetravel.diary.dto.RegisterResponse;
import com.timetravel.diary.entity.Role;
import com.timetravel.diary.entity.User;
import com.timetravel.diary.exception.RoleNotFoundException;
import com.timetravel.diary.repository.RoleRepository;
import com.timetravel.diary.repository.UserRepository;
import com.timetravel.diary.service.AuthService;
import com.timetravel.diary.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogService logService;

    private static final String DEFAULT_ROLE = "TRAVELER";

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (isAlreadyAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RegisterResponse("You are already logged in. Logout first.", null));
        }

        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        Role travelerRole = roleRepository.findByName(DEFAULT_ROLE)
            .orElseThrow(() -> new RoleNotFoundException("Default role not found"));

        user.setRoles(Set.of(travelerRole));

        User savedUser = userRepository.save(user);
        logService.save(savedUser.getId(), "USER_REGISTERED", "User registered successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new RegisterResponse("User registered successfully", registerRequest.username()));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and issue a JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        if (isAlreadyAuthenticated()) {
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new AuthResponse(null, null, currentAuth.getName(), "You are already logged in. Logout first."));
        }

        AuthResponse response = authService.login(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user and invalidate refresh token")
    public ResponseEntity<String> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            authService.logout(auth.getName());
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in");
    }

    private boolean isAlreadyAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName());
    }
}
