package com.freelancer.freelancer_platform.controller;

import com.freelancer.freelancer_platform.dto.UserRegisterRequest;
import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getByUserId(id);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody @Valid UserRegisterRequest request) {
        UserResponse userResponse = userService.updateUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");

    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInformation() {
        UserResponse response = userService.getMyInformation();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String surname,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        Page<UserResponse> responses = userService.searchUsers(name, surname, page, size);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/freelancer")
    public ResponseEntity<UserResponse> matchFreelancer(@RequestParam @Valid String skill) {
        UserResponse matched = userService.matchRandomFreelancer(skill);
        System.out.println(matched);
        return ResponseEntity.ok(matched);

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam boolean isActive) {
        System.out.println("Mecidov Cavid");
        userService.updateOnlileStatus(isActive
        );
        return ResponseEntity.ok("Status updated successfully to " + isActive);
    }
}


