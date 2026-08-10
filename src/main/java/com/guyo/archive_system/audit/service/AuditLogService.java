package com.guyo.archive_system.audit.service;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.audit.dto.AuditLogDto;
import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;

public interface AuditLogService {

    void log(

            UUID actorId,

            AuditAction action,

            ResourceType resourceType,

            UUID resourceId,

            String details,

            InetAddress ipAddress

    );

    List<AuditLogDto> getByResource(UUID resourceId);

}