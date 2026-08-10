package com.guyo.archive_system.user.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private UUID userSub;

    private String fullName;

    private String email;

    private UUID departmentId;

    private Boolean isActive;
}