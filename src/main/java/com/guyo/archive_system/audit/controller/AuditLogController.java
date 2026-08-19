package com.guyo.archive_system.audit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.guyo.archive_system.audit.dto.AuditLogDto;
import com.guyo.archive_system.audit.service.AuditLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/{resourceId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR', 'ARCHIVE_OFFICER', 'RECORDS_MANAGER')")
    public List<AuditLogDto> getAuditLogs(
            @PathVariable UUID resourceId) {

        return auditLogService.getByResource(resourceId);

    }

}
