package com.guyo.archive_system.document.dto;

import com.guyo.archive_system.document.enums.ClassificationLevel;
import com.guyo.archive_system.document.enums.DocumentStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

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

    private UUID createdBy;

    private OffsetDateTime createdAt;

}