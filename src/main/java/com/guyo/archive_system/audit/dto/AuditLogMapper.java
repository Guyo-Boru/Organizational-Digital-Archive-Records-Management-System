package com.guyo.archive_system.audit.dto;

import com.guyo.archive_system.audit.entity.AuditLog;

public class AuditLogMapper {

    private AuditLogMapper() {}

    public static AuditLogDto toDto(AuditLog log) {

        return AuditLogDto.builder()

                .auditLogId(log.getAuditLogId())

                .actorId(log.getActorId())

                .action(log.getAction())

                .resourceType(log.getResourceType())

                .resourceId(log.getResourceId())

                .details(log.getDetails())

                .ipAddress(
                        log.getIpAddress() == null
                                ? null
                                : log.getIpAddress().getHostAddress()
                )

                .createdAt(log.getCreatedAt())

                .build();

    }

}