package com.guyo.archive_system.document.version.mapper;

import com.guyo.archive_system.document.version.dto.DocumentVersionDto;
import com.guyo.archive_system.document.version.entity.DocumentVersion;
import org.springframework.stereotype.Component;

@Component
public class DocumentVersionMapper {

    public DocumentVersionDto toDto(DocumentVersion version) {

        return DocumentVersionDto.builder()
                .versionId(version.getVersionId())
                .documentId(version.getDocument().getDocumentId())
                .versionNumber(version.getVersionNumber())
                .originalFileName(version.getOriginalFileName())
                .storedFileName(version.getStoredFileName())
                .filePath(version.getFilePath())
                .mimeType(version.getMimeType())
                .fileSize(version.getFileSize())
                .checksumSha256(version.getChecksumSha256())
                .uploadedAt(version.getUploadedAt())
                .uploadedBy(version.getUploadedBy().getUserSub())
                .build();
    }

}