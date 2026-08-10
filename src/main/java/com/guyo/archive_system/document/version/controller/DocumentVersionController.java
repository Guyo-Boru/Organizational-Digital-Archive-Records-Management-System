package com.guyo.archive_system.document.version.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guyo.archive_system.document.version.dto.UploadDocumentVersionResponse;
import com.guyo.archive_system.document.version.service.DocumentVersionService;

@RestController
@RequestMapping("/api/v1/documents/{documentId}/versions")
public class DocumentVersionController {

    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(
            DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UploadDocumentVersionResponse upload(

            @PathVariable UUID documentId,

            @RequestPart("file") MultipartFile file,

            @AuthenticationPrincipal Jwt jwt

    ) {

        UUID uploadedBy = UUID.fromString(jwt.getSubject());

        return documentVersionService.uploadVersion(
                documentId,
                file,
                uploadedBy
        );

    }

}