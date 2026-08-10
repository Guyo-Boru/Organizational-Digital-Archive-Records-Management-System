package com.guyo.archive_system.document.service;

import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.mapper.DocumentMapper;
import com.guyo.archive_system.document.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public List<DocumentDto> getAll() {

        List<Document> documents = documentRepository.findAll();

        return documentMapper.toDtoList(documents);
    }

    @Override
    public DocumentDto getById(UUID documentId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Document not found with ID: " + documentId));

        return documentMapper.toDto(document);
    }

}