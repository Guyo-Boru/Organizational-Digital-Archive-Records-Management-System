package com.guyo.archive_system.document.mapper;

import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.entity.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentMapper {

    public DocumentDto toDto(Document document) {

        return DocumentDto.builder()
                .documentId(document.getDocumentId())
                .referenceNumber(document.getReferenceNumber())
                .title(document.getTitle())
                .description(document.getDescription())
                .categoryId(document.getCategory().getCategoryId())
                .departmentId(document.getDepartment().getDepartmentId())
                .classification(document.getClassification())
                .status(document.getStatus())
                .createdBy(document.getCreatedBy().getUserSub())
                .createdAt(document.getCreatedAt())
                .build();
    }

    public List<DocumentDto> toDtoList(List<Document> documents) {

        return documents.stream()
                .map(this::toDto)
                .toList();
    }

}