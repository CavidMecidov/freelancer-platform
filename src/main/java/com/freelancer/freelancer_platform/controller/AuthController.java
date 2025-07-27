package com.freelancer.freelancer_platform.controller;

import com.freelancer.freelancer_platform.dto.LoginResponse;
import com.freelancer.freelancer_platform.dto.UserLoginRequest;
import com.freelancer.freelancer_platform.dto.UserRegisterRequest;
import com.freelancer.freelancer_platform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid UserRegisterRequest registerRequest) {
        LoginResponse loginResponse = authService.register(registerRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        LoginResponse loginResponse = authService.Login(userLoginRequest);
        return ResponseEntity.ok(loginResponse);
    }

}
