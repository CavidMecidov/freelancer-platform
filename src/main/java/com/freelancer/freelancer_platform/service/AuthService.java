package com.freelancer.freelancer_platform.service;

import com.freelancer.freelancer_platform.dto.LoginResponse;
import com.freelancer.freelancer_platform.dto.UserLoginRequest;
import com.freelancer.freelancer_platform.dto.UserRegisterRequest;
import com.freelancer.freelancer_platform.entity.User;
import com.freelancer.freelancer_platform.enums.Role;
import com.freelancer.freelancer_platform.exception.ConflictException;
import com.freelancer.freelancer_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email or username already exist.");
        }
        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.FREELANCER)
                .isActive(true)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateAccessToken(user);
        return new LoginResponse(jwtToken);

    }

    public LoginResponse Login(UserLoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());

        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActive()) {
            user.setActive(true);
            userRepository.save(user);
        }

        String jwtToken = jwtService.generateAccessToken(user);
        return new LoginResponse(jwtToken);
    }

}
