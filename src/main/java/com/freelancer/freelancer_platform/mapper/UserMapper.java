package com.freelancer.freelancer_platform.mapper;

import com.freelancer.freelancer_platform.dto.UserResponse;
import com.freelancer.freelancer_platform.entity.Skill;
import com.freelancer.freelancer_platform.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default List<String> mapSkills(List<Skill> skills) {
        if (skills == null) return new ArrayList<>();
        return skills.stream().map(Skill::getName).collect(Collectors.toList());
    }

    @Mapping(target = "skills", expression = "java(mapSkills(user.getSkills()))")
    UserResponse toResponse(User user);

    @Mapping(target = "skills", ignore = true)
    User toEntity(UserResponse response);
}

