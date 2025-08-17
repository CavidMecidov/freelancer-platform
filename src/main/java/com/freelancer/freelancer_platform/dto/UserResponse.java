package com.freelancer.freelancer_platform.dto;

import com.freelancer.freelancer_platform.enums.Gender;
import com.freelancer.freelancer_platform.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private Gender gender;
    private String email;
    private boolean isActive;
 }
