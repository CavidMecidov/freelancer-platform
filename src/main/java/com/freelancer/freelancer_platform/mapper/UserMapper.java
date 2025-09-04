package com.freelancer.freelancer_platform.mapper;

import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserResponse response);
    UserResponse toResponse(User user);
}
