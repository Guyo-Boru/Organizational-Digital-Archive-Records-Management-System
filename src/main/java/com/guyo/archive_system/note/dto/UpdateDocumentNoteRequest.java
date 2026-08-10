package com.guyo.archive_system.note.dto;

import com.guyo.archive_system.note.enums.NoteType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDocumentNoteRequest {

    @NotNull
    private NoteType noteType;

    @NotBlank
    private String note;

}