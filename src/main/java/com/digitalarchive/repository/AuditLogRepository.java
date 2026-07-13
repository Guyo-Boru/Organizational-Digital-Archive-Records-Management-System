package com.digitalarchive.repository;

import com.digitalarchive.domain.entity.AuditLog;
import com.digitalarchive.domain.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByActorIdOrderByCreatedAtDesc(String actorId);
    List<AuditLog> findByResourceTypeAndResourceIdOrderByCreatedAtDesc(ResourceType resourceType, String resourceId);
}