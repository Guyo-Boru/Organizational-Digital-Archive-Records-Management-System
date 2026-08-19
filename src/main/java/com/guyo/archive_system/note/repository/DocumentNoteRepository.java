package com.guyo.archive_system.note.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.note.entity.DocumentNote;

public interface DocumentNoteRepository
        extends JpaRepository<DocumentNote, UUID> {

    Page<DocumentNote> findByDocumentDocumentIdAndDeletedAtIsNull(
            UUID documentId,
            Pageable pageable
    );

    /**
     * Scoped lookup used by update/delete so a caller cannot mutate a note
     * by guessing its id while supplying an unrelated {@code documentId}
     * in the URL path (IDOR protection).
     */
    Optional<DocumentNote> findByNoteIdAndDocumentDocumentIdAndDeletedAtIsNull(
            UUID noteId,
            UUID documentId
    );

}