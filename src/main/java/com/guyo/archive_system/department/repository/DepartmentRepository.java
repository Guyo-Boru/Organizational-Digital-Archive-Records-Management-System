package com.guyo.archive_system.department.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.department.entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID> {

    Optional<Department> findByDepartmentIdAndDeletedAtIsNull(UUID departmentId);

    List<Department> findByDeletedAtIsNull();

}