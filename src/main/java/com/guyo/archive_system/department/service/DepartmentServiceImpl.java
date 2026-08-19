package com.guyo.archive_system.department.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.common.exception.ResourceNotFoundException;
import com.guyo.archive_system.department.dto.DepartmentDto;
import com.guyo.archive_system.department.entity.Department;
import com.guyo.archive_system.department.mapper.DepartmentMapper;
import com.guyo.archive_system.department.repository.DepartmentRepository;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository
    ) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto getById(UUID departmentId) {

        Department department = departmentRepository
                .findByDepartmentIdAndDeletedAtIsNull(departmentId)
                .orElseThrow(() ->
        new ResourceNotFoundException(
                "Department not found: " + departmentId
        )
);

        return DepartmentMapper.toDto(department);
    }

    @Override
    public List<DepartmentDto> getAll() {

        return departmentRepository
                .findByDeletedAtIsNull()
                .stream()
                .map(DepartmentMapper::toDto)
                .toList();
    }
}