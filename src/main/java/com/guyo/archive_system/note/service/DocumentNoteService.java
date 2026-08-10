package com.guyo.archive_system.note.service;

import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.note.dto.CreateDocumentNoteRequest;
import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.dto.UpdateDocumentNoteRequest;

public interface DocumentNoteService {

    DocumentNoteDto create(
            UUID documentId,
            UUID currentUserId,
            CreateDocumentNoteRequest request
    );

    List<DocumentNoteDto> getByDocument(UUID documentId);

    DocumentNoteDto update(
            UUID noteId,
            UUID currentUserId,
            UpdateDocumentNoteRequest request
    );

    void delete(
            UUID noteId,
            UUID currentUserId
    );

}