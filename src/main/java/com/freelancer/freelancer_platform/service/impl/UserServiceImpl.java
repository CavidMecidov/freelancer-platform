package com.freelancer.freelancer_platform.service.impl;

import com.freelancer.freelancer_platform.config.PasswordEncoderConfig;
import com.freelancer.freelancer_platform.dto.UserRegisterRequest;
import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.entity.User;
import com.freelancer.freelancer_platform.enums.Role;
import com.freelancer.freelancer_platform.mapper.UserMapper;
import com.freelancer.freelancer_platform.repository.UserRepository;
import com.freelancer.freelancer_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse updateUser(UserRegisterRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.getEmail().equals(email)) {
            throw new AccessDeniedException("User can only update own account");
        }
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setBirthday(request.getBirthday());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword())); //
        }

        User userUp = userRepository.save(user);
        return userMapper.toResponse(userUp);
    }

    @Override
    public void deleteUser(Long id) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.getId().equals(id)) {
            throw new AccessDeniedException("User can only delete own account");
        }
        userRepository.delete(user);
    }

    @Override
    public UserResponse getByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getMyInformation() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> searchUsers(String name, String surname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.searchUsers(name, surname, pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    public UserResponse matchRandomFreelancer(String skill) {
        List<User> candidates = userRepository.findByRoleAndIsActiveAndSkillName(Role.USER, true, skill);
        if (candidates.isEmpty()) {
            throw new RuntimeException("No online freelancer found for skill:" + skill);
        }
        Collections.shuffle(candidates);
        User matched = candidates.get(0);
        return userMapper.toResponse(matched);
    }

    @Override
    public void updateOnlileStatus(boolean isActive) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currenUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        currenUser.setActive(isActive);
        userRepository.save(currenUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}


