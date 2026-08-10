package com.guyo.archive_system.audit.service;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.audit.dto.AuditLogDto;
import com.guyo.archive_system.audit.dto.AuditLogMapper;
import com.guyo.archive_system.audit.entity.AuditLog;
import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;
import com.guyo.archive_system.audit.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;

    @Override
    public void log(

            UUID actorId,

            AuditAction action,

            ResourceType resourceType,

            UUID resourceId,

            String details,

            InetAddress ipAddress

    ) {

        AuditLog log = AuditLog.builder()

                .actorId(actorId)

                .action(action)

                .resourceType(resourceType)

                .resourceId(resourceId)

                .details(details)

                .ipAddress(ipAddress)

                .build();

        repository.save(log);

    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogDto> getByResource(UUID resourceId) {

        return repository

                .findByResourceIdOrderByCreatedAtDesc(resourceId)

                .stream()

                .map(AuditLogMapper::toDto)

                .toList();

    }

}