package com.guyo.archive_system.document.controller;

import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public List<DocumentDto> getAll() {

        return documentService.getAll();
    }

    @GetMapping("/{documentId}")
    public DocumentDto getById(
            @PathVariable UUID documentId) {

        return documentService.getById(documentId);
    }

}