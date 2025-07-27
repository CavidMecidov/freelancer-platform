package com.freelancer.freelancer_platform.dto;

import com.freelancer.freelancer_platform.enums.Gender;
import com.freelancer.freelancer_platform.enums.Role;
import jakarta.annotation.Nullable;

public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean isActive;
 }
