package com.freelancer.freelancer_platform.dto;

import com.freelancer.freelancer_platform.entity.Skill;
import com.freelancer.freelancer_platform.enums.Gender;
import com.freelancer.freelancer_platform.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private Gender gender;
    private String email;
    private boolean isActive;
    private List<String> skills;
 }
