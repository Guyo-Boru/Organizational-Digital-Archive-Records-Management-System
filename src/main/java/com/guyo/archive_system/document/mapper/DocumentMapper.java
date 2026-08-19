package com.guyo.archive_system.document.mapper;

import org.springframework.stereotype.Component;

import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.entity.Document;

@Component
public class DocumentMapper {

    public DocumentDto toDto(Document document) {

        if (document == null) {
            return null;
        }

        return DocumentDto.builder()

                .documentId(
                    document.getDocumentId()
                )

                .referenceNumber(
                    document.getReferenceNumber()
                )

                .title(
                    document.getTitle()
                )

                .description(
                    document.getDescription()
                )

                .categoryId(
                    document.getCategory() != null
                        ? document.getCategory().getCategoryId()
                        : null
                )

                .departmentId(
                    document.getDepartment() != null
                        ? document.getDepartment().getDepartmentId()
                        : null
                )

                .classification(
                    document.getClassification()
                )

                .status(
                    document.getStatus()
                )

                .currentVersionId(
                    document.getCurrentVersionId()
                )

                .createdBy(
                    document.getCreatedBy() != null
                        ? document.getCreatedBy().getUserSub()
                        : null
                )

                .updatedBy(
                    document.getUpdatedBy() != null
                        ? document.getUpdatedBy().getUserSub()
                        : null
                )

                .createdAt(
                    document.getCreatedAt()
                )

                .updatedAt(
                    document.getUpdatedAt()
                )

                .archivedAt(
                    document.getArchivedAt()
                )

                .build();
    }
}