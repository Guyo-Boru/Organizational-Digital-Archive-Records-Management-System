package com.guyo.archive_system.document.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.dto.DocumentSearchRequest;

public interface DocumentService {

    DocumentDto getById(UUID documentId);

    DocumentDto getByReferenceNumber(String referenceNumber);

    Page<DocumentDto> getAll(Pageable pageable);

    /**
     * Filters/searches documents. All criteria in {@code request} are
     * optional and combined with AND; list-valued criteria are matched
     * with OR within that field.
     *
     * @param currentUserId the requesting user, recorded in the audit log
     *                      whenever {@code request.includeDeleted} is
     *                      true, so access to soft-deleted documents is
     *                      traceable even though it isn't yet role-gated.
     */
    Page<DocumentDto> search(
            DocumentSearchRequest request,
            Pageable pageable,
            UUID currentUserId
    );

    /**
     * Soft-deletes a document (sets deletedAt/deletedBy). The document and
     * its full version/note/workflow history are preserved and can be
     * recovered with {@link #restore}; nothing is physically removed.
     */
    void delete(UUID documentId, UUID currentUserId);

    /** Reverses a soft delete performed via {@link #delete}. */
    DocumentDto restore(UUID documentId, UUID currentUserId);
}
