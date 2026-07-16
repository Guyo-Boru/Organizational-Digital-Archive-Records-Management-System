package com.guyo.archive_system.department.dto;

import java.util.UUID;

public record DepartmentDto(
        UUID departmentId,
        String name,
        String description,
        UUID parentDepartmentId
) {
}