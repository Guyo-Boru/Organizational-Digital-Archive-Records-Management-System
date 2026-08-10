package com.guyo.archive_system.document.version.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadDocumentVersionResponse {

    private UUID versionId;

    private Integer versionNumber;

    private String originalFilename;

    private String storedFilename;

    private String mimeType;

    private Long fileSizeBytes;

    private String checksumSha256;

    private OffsetDateTime createdAt;

}