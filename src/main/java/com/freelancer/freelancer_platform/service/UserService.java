package com.freelancer.freelancer_platform.service;

import com.freelancer.freelancer_platform.dto.UserRegisterRequest;
import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserResponse updateUser(UserRegisterRequest request);

    void deleteUser(Long id);

    UserResponse getByUserId(Long id);

    UserResponse getMyInformation();

    Page<UserResponse> searchUsers(String name, String surname, int page, int size);
    UserResponse matchRandomFreelancer(String skill);


}
