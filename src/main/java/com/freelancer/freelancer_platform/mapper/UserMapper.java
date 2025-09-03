package com.freelancer.freelancer_platform.mapper;

import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserResponse userResponse);
    UserResponse toResponse(User user);
}
