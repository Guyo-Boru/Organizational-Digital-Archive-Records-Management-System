package com.guyo.archive_system.department.repository;

import com.guyo.archive_system.department.entity.Department;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID> {

}