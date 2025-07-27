package com.freelancer.freelancer_platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freelancer.freelancer_platform.enums.Gender;
import com.freelancer.freelancer_platform.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
    @NotNull(message = "Age cannot be blank")
    @Min(value = 18, message = "You must be at least 18 years old to register")
    private Long age;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is invalid ")
    private String email;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotNull(message = "Birthday cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private Gender gender;
    private Role role;
}
