package com.guyo.archive_system.note.service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;
import com.guyo.archive_system.audit.service.AuditLogService;
import com.guyo.archive_system.common.exception.InvalidStateException;
import com.guyo.archive_system.common.exception.ResourceNotFoundException;
import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.repository.DocumentRepository;
import com.guyo.archive_system.note.dto.CreateDocumentNoteRequest;
import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.dto.UpdateDocumentNoteRequest;
import com.guyo.archive_system.note.entity.DocumentNote;
import com.guyo.archive_system.note.enums.NoteType;
import com.guyo.archive_system.note.mapper.DocumentNoteMapper;
import com.guyo.archive_system.note.repository.DocumentNoteRepository;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentNoteServiceImpl implements DocumentNoteService {

    private final DocumentRepository documentRepository;

    private final DocumentNoteRepository noteRepository;

    private final UserRepository userRepository;

    private final AuditLogService auditLogService;

    @Override
    public DocumentNoteDto create(
            UUID documentId,
            UUID currentUserId,
            CreateDocumentNoteRequest request
    ) {

        requireUserCreatable(request.getNoteType());

        Document document = documentRepository.findById(documentId)
                .filter(doc -> doc.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found: " + documentId));

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + currentUserId));

        DocumentNote note = DocumentNote.builder()
                .document(document)
                .noteType(request.getNoteType())
                .note(request.getNote())
                .createdBy(user)
                .updatedBy(user)
                .build();

        DocumentNote saved = noteRepository.save(note);

        auditLogService.log(
                currentUserId,
                AuditAction.CREATE,
                ResourceType.DOCUMENT_NOTE,
                saved.getNoteId(),
                Map.of(
                        "documentId", documentId,
                        "noteType", saved.getNoteType()
                )
        );

        return DocumentNoteMapper.toDto(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentNoteDto> getByDocument(UUID documentId, Pageable pageable) {

        return noteRepository
                .findByDocumentDocumentIdAndDeletedAtIsNull(documentId, pageable)
                .map(DocumentNoteMapper::toDto);

    }

    @Override
    public DocumentNoteDto update(
            UUID documentId,
            UUID noteId,
            UUID currentUserId,
            UpdateDocumentNoteRequest request
    ) {

        requireUserCreatable(request.getNoteType());

        DocumentNote note = findOwnedNote(documentId, noteId);

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + currentUserId));

        note.setNoteType(request.getNoteType());
        note.setNote(request.getNote());
        note.setUpdatedBy(user);
        note.setUpdatedAt(OffsetDateTime.now());

        DocumentNote saved = noteRepository.save(note);

        auditLogService.log(
                currentUserId,
                AuditAction.UPDATE,
                ResourceType.DOCUMENT_NOTE,
                saved.getNoteId(),
                Map.of("documentId", documentId)
        );

        return DocumentNoteMapper.toDto(saved);

    }

    @Override
    public void delete(
            UUID documentId,
            UUID noteId,
            UUID currentUserId
    ) {

        DocumentNote note = findOwnedNote(documentId, noteId);

        note.setDeletedAt(OffsetDateTime.now());
        note.setDeletedBy(currentUserId);

        noteRepository.save(note);

        auditLogService.log(
                currentUserId,
                AuditAction.DELETE,
                ResourceType.DOCUMENT_NOTE,
                noteId,
                Map.of("documentId", documentId)
        );

    }

    /**
     * Loads a note and verifies it belongs to {@code documentId}, so a
     * caller cannot mutate/delete a note on a document they didn't
     * reference by supplying a valid {@code noteId} from elsewhere.
     */
    private DocumentNote findOwnedNote(UUID documentId, UUID noteId) {

        return noteRepository
                .findByNoteIdAndDocumentDocumentIdAndDeletedAtIsNull(noteId, documentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Note not found: " + noteId + " for document: " + documentId));

    }

    private void requireUserCreatable(NoteType noteType) {

        if (!noteType.isUserCreatable()) {
            throw new InvalidStateException(
                    "Note type " + noteType + " cannot be set directly by a client");
        }

    }

}
