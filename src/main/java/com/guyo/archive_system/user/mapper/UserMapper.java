package com.guyo.archive_system.user.mapper;

import org.springframework.stereotype.Component;

import com.guyo.archive_system.user.dto.UserDto;
import com.guyo.archive_system.user.entity.User;

@Component
public class UserMapper {

    public UserDto toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();

        dto.setUserSub(user.getUserSub());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setDepartmentId(user.getDepartmentId());
        dto.setIsActive(user.getIsActive());

        return dto;
    }
}