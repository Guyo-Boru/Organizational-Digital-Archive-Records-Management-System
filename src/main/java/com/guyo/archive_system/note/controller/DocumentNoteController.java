package com.guyo.archive_system.note.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guyo.archive_system.common.response.PageResponse;
import com.guyo.archive_system.note.dto.CreateDocumentNoteRequest;
import com.guyo.archive_system.note.dto.DocumentNoteDto;
import com.guyo.archive_system.note.dto.UpdateDocumentNoteRequest;
import com.guyo.archive_system.note.service.DocumentNoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents/{documentId}/notes")
public class DocumentNoteController {

    private final DocumentNoteService documentNoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentNoteDto create(

            @PathVariable UUID documentId,

            @Validated
            @RequestBody CreateDocumentNoteRequest request,

            @AuthenticationPrincipal Jwt jwt

    ) {

        return documentNoteService.create(

                documentId,

                UUID.fromString(jwt.getSubject()),

                request

        );

    }

    @GetMapping
    public PageResponse<DocumentNoteDto> getAll(

            @PathVariable UUID documentId,

            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable

    ) {

        return PageResponse.of(
                documentNoteService.getByDocument(documentId, pageable)
        );

    }

    @PutMapping("/{noteId}")
    public DocumentNoteDto update(

            @PathVariable UUID documentId,

            @PathVariable UUID noteId,

            @Validated
            @RequestBody UpdateDocumentNoteRequest request,

            @AuthenticationPrincipal Jwt jwt

    ) {

        return documentNoteService.update(

                documentId,

                noteId,

                UUID.fromString(jwt.getSubject()),

                request

        );

    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(

            @PathVariable UUID documentId,

            @PathVariable UUID noteId,

            @AuthenticationPrincipal Jwt jwt

    ) {

        documentNoteService.delete(

                documentId,

                noteId,

                UUID.fromString(jwt.getSubject())

        );

    }

}
