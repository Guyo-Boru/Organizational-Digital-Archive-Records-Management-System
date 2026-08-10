package com.guyo.archive_system.note.mapper;

import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.entity.DocumentNote;

public class DocumentNoteMapper {

    private DocumentNoteMapper() {
    }

    public static DocumentNoteDto toDto(DocumentNote note) {

        return DocumentNoteDto.builder()

                .noteId(note.getNoteId())

                .documentId(note.getDocument().getDocumentId())

                .noteType(note.getNoteType())

                .note(note.getNote())

                .createdAt(note.getCreatedAt())

                .createdBy(note.getCreatedBy().getUserSub())

                .updatedAt(note.getUpdatedAt())

                .updatedBy(
                        note.getUpdatedBy() != null
                                ? note.getUpdatedBy().getUserSub()
                                : null
                )

                .build();

    }

}