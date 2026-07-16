package com.guyo.archive_system.department.service;

import com.guyo.archive_system.department.dto.DepartmentDto;
import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    DepartmentDto getById(UUID departmentId);

    List<DepartmentDto> getAll();

}