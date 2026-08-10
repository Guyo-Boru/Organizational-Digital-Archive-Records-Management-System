package com.guyo.archive_system.document.version.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.document.version.entity.DocumentVersion;

public interface DocumentVersionRepository
        extends JpaRepository<DocumentVersion, UUID> {

    Optional<DocumentVersion>
    findTopByDocumentDocumentIdOrderByVersionNumberDesc(
            UUID documentId
    );

}