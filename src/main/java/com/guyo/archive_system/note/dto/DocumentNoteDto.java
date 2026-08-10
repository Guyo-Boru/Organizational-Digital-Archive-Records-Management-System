package com.guyo.archive_system.note.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.guyo.archive_system.note.enums.NoteType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentNoteDto {

    private UUID noteId;

    private UUID documentId;

    private NoteType noteType;

    private String note;

    private OffsetDateTime createdAt;

    private UUID createdBy;

    private OffsetDateTime updatedAt;

    private UUID updatedBy;

}