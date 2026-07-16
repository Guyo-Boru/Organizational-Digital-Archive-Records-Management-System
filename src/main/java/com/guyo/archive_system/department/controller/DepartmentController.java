package com.guyo.archive_system.department.controller;

import com.guyo.archive_system.department.dto.DepartmentDto;
import com.guyo.archive_system.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(
            @PathVariable UUID departmentId
    ) {
        return ResponseEntity.ok(
                departmentService.getDepartmentById(departmentId)
        );
    }
}