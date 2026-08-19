package com.guyo.archive_system.document.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guyo.archive_system.document.entity.Document;

import jakarta.persistence.LockModeType;

public interface DocumentRepository
        extends JpaRepository<Document, UUID>,
                JpaSpecificationExecutor<Document> {

    Optional<Document> findByReferenceNumber(
            String referenceNumber
    );

    boolean existsByReferenceNumber(
            String referenceNumber
    );

    /**
     * Locks the document row while a new version is being created.
     *
     * This prevents two concurrent uploads for the same document from
     * calculating the same next version number.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT d
            FROM Document d
            WHERE d.documentId = :documentId
            """)
    Optional<Document> findByIdForUpdate(
            @Param("documentId") UUID documentId
    );
}