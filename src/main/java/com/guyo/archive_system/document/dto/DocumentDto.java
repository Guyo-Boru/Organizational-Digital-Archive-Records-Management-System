package com.guyo.archive_system.document.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.guyo.archive_system.document.enums.ClassificationLevel;
import com.guyo.archive_system.document.enums.DocumentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {

    private UUID documentId;

    private String referenceNumber;

    private String title;

    private String description;

    private UUID categoryId;

    private UUID departmentId;

    private ClassificationLevel classification;

    private DocumentStatus status;

    private UUID currentVersionId;

    private UUID createdBy;

    private UUID updatedBy;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime archivedAt;

}