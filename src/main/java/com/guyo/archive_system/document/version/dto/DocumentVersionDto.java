package com.guyo.archive_system.document.version.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVersionDto {

    private UUID versionId;

    private UUID documentId;

    private Integer versionNumber;

    private String originalFileName;

    private String storedFileName;

    private String filePath;

    private String mimeType;

    private Long fileSize;

    private String checksumSha256;

    private OffsetDateTime uploadedAt;

    private UUID uploadedBy;

}