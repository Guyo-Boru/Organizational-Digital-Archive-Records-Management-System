package com.guyo.archive_system.audit.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;

import lombok.Builder;

@Builder
public record AuditLogDto(

        UUID auditLogId,

        UUID actorId,

        AuditAction action,

        ResourceType resourceType,

        UUID resourceId,

        String details,

        String ipAddress,

        OffsetDateTime createdAt

) {}