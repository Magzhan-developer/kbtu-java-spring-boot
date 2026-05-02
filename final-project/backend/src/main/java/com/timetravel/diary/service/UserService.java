package com.timetravel.diary.service;


import com.timetravel.diary.dto.UserResponse;
import com.timetravel.diary.dto.UserUpdateRequest;
import com.timetravel.diary.entity.User;
import com.timetravel.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
            .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getUsername().equals(request.username()) &&
            userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (!user.getEmail().equals(request.email()) &&
            userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setUsername(request.username());
        user.setEmail(request.email());
        userRepository.save(user);

        log.info("User {} updated successfully", user.getUsername());
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.delete(user);
        log.info("User {} deleted successfully", user.getUsername());
        logService.save(userId, "USER_DELETED", "User deleted successfully");
    }

    public boolean isCurrentUserOrAdmin(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        User currentUser = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new IllegalStateException("Current user not found"));
        return currentUser.getId().equals(userId);
    }
}
