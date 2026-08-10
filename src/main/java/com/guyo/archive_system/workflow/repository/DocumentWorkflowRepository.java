package com.guyo.archive_system.workflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.workflow.entity.DocumentWorkflowHistory;

public interface DocumentWorkflowRepository
        extends JpaRepository<DocumentWorkflowHistory, UUID> {

List<DocumentWorkflowHistory>
        findByDocumentDocumentIdOrderByChangedAtDesc(UUID documentId);

}