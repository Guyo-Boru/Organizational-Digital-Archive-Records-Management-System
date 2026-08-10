package com.guyo.archive_system.note.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.repository.DocumentRepository;
import com.guyo.archive_system.note.dto.CreateDocumentNoteRequest;
import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.dto.UpdateDocumentNoteRequest;
import com.guyo.archive_system.note.entity.DocumentNote;
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

    @Override
    public DocumentNoteDto create(
            UUID documentId,
            UUID currentUserId,
            CreateDocumentNoteRequest request
    ) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found."));

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        DocumentNote note = DocumentNote.builder()
                .document(document)
                .noteType(request.getNoteType())
                .note(request.getNote())
                .createdBy(user)
                .updatedBy(user)
                .build();

        return DocumentNoteMapper.toDto(
                noteRepository.save(note)
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentNoteDto> getByDocument(UUID documentId) {

        return noteRepository
                .findByDocumentDocumentIdAndDeletedAtIsNullOrderByCreatedAtDesc(documentId)
                .stream()
                .map(DocumentNoteMapper::toDto)
                .toList();

    }

    @Override
    public DocumentNoteDto update(
            UUID noteId,
            UUID currentUserId,
            UpdateDocumentNoteRequest request
    ) {

        DocumentNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found."));

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        note.setNoteType(request.getNoteType());
        note.setNote(request.getNote());
        note.setUpdatedBy(user);
        note.setUpdatedAt(OffsetDateTime.now());

        return DocumentNoteMapper.toDto(
                noteRepository.save(note)
        );

    }

    @Override
    public void delete(
            UUID noteId,
            UUID currentUserId
    ) {

        DocumentNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found."));

        note.setDeletedAt(OffsetDateTime.now());
        note.setDeletedBy(currentUserId);

        noteRepository.save(note);

    }

}