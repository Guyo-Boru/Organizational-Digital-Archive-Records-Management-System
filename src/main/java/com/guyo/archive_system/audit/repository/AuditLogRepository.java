package com.guyo.archive_system.audit.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.audit.entity.AuditLog;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByResourceIdOrderByCreatedAtDesc(UUID resourceId);

}