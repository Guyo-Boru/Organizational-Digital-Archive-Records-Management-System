package com.guyo.archive_system.document.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.guyo.archive_system.common.response.PageResponse;
import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.dto.DocumentSearchRequest;
import com.guyo.archive_system.document.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{documentId}")
    public DocumentDto getById(
            @PathVariable UUID documentId) {

        return documentService.getById(documentId);
    }

    @GetMapping("/reference/{referenceNumber}")
    public DocumentDto getByReferenceNumber(
            @PathVariable String referenceNumber) {

        return documentService.getByReferenceNumber(referenceNumber);
    }

    @GetMapping
    public PageResponse<DocumentDto> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return PageResponse.of(
                documentService.getAll(pageable)
        );
    }

    /**
     * Filters/searches documents. All criteria are optional and combined
     * with AND; list-valued criteria (statuses, classifications, category
     * and department ids) are matched with OR within that field. Supports
     * standard Spring pagination/sorting query params, e.g.
     * {@code ?page=0&size=20&sort=title,asc}.
     */
    @GetMapping("/search")
    public PageResponse<DocumentDto> search(
            @Valid @ModelAttribute DocumentSearchRequest request,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal Jwt jwt) {

        return PageResponse.of(
                documentService.search(
                        request,
                        pageable,
                        UUID.fromString(jwt.getSubject())
                )
        );
    }

    /**
     * Soft-deletes a document. The document and its full version, note and
     * workflow history are preserved and can be recovered via
     * {@link #restore}.
     */
    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID documentId,
            @AuthenticationPrincipal Jwt jwt) {

        documentService.delete(
                documentId,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{documentId}/restore")
    public DocumentDto restore(
            @PathVariable UUID documentId,
            @AuthenticationPrincipal Jwt jwt) {

        return documentService.restore(
                documentId,
                UUID.fromString(jwt.getSubject())
        );
    }
}
