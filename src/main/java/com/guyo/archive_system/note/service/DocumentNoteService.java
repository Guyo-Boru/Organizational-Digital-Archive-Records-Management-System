package com.guyo.archive_system.note.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guyo.archive_system.note.dto.CreateDocumentNoteRequest;
import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.dto.UpdateDocumentNoteRequest;

public interface DocumentNoteService {

    DocumentNoteDto create(
            UUID documentId,
            UUID currentUserId,
            CreateDocumentNoteRequest request
    );

    Page<DocumentNoteDto> getByDocument(UUID documentId, Pageable pageable);

    /**
     * @param documentId the document the note is expected to belong to,
     *                   taken from the URL path. The update is rejected
     *                   with {@code ResourceNotFoundException} if the note
     *                   does not belong to this document.
     */
    DocumentNoteDto update(
            UUID documentId,
            UUID noteId,
            UUID currentUserId,
            UpdateDocumentNoteRequest request
    );

    /**
     * @param documentId see {@link #update}.
     */
    void delete(
            UUID documentId,
            UUID noteId,
            UUID currentUserId
    );

}