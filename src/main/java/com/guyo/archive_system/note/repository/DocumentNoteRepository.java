package com.guyo.archive_system.note.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.note.entity.DocumentNote;

public interface DocumentNoteRepository
        extends JpaRepository<DocumentNote, UUID> {

    List<DocumentNote> findByDocumentDocumentIdAndDeletedAtIsNullOrderByCreatedAtDesc(
            UUID documentId
    );

}