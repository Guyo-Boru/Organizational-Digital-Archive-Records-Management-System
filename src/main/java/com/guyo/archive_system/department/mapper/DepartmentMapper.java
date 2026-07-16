package com.guyo.archive_system.department.mapper;

import com.guyo.archive_system.department.dto.DepartmentDto;
import com.guyo.archive_system.department.entity.Department;

public class DepartmentMapper {

    private DepartmentMapper() {
        // Utility class
    }

    public static DepartmentDto toDto(Department department) {

        if (department == null) {
            return null;
        }

        return new DepartmentDto(
                department.getDepartmentId(),
                department.getName(),
                department.getDescription(),
                department.getParentDepartment() != null
                        ? department.getParentDepartment().getDepartmentId()
                        : null
        );
    }
}