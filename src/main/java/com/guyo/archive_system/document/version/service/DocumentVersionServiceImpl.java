package com.guyo.archive_system.document.version.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.repository.DocumentRepository;
import com.guyo.archive_system.document.version.dto.UploadDocumentVersionResponse;
import com.guyo.archive_system.document.version.entity.DocumentVersion;
import com.guyo.archive_system.document.version.repository.DocumentVersionRepository;
import com.guyo.archive_system.storage.dto.StoredFileDto;
import com.guyo.archive_system.storage.service.StorageService;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentVersionServiceImpl implements DocumentVersionService {

    private final DocumentRepository documentRepository;

    private final DocumentVersionRepository versionRepository;

    private final UserRepository userRepository;

    private final StorageService storageService;

    @Override
    public UploadDocumentVersionResponse uploadVersion(
            UUID documentId,
            MultipartFile file,
            UUID currentUserId
    ) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new RuntimeException("Document not found."));

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        Integer versionNumber =
                versionRepository
                        .findTopByDocumentDocumentIdOrderByVersionNumberDesc(documentId)
                        .map(v -> v.getVersionNumber() + 1)
                        .orElse(1);

        StoredFileDto storedFile = storageService.store(file);

        DocumentVersion version = DocumentVersion.builder()
                .document(document)
                .versionNumber(versionNumber)
                .originalFileName(storedFile.getOriginalFileName())
                .storedFileName(storedFile.getStoredFileName())
                .filePath(storedFile.getFilePath())
                .mimeType(storedFile.getMimeType())
                .fileSize(storedFile.getFileSize())
                .checksumSha256(storedFile.getChecksumSha256())
                .uploadedBy(user)
                .build();

        DocumentVersion savedVersion =
                versionRepository.save(version);

        document.setCurrentVersionId(
                savedVersion.getVersionId()
        );

        documentRepository.save(document);

        return UploadDocumentVersionResponse.builder()
                .versionId(savedVersion.getVersionId())
                .versionNumber(savedVersion.getVersionNumber())
                .originalFilename(savedVersion.getOriginalFileName())
                .storedFilename(savedVersion.getStoredFileName())
                .mimeType(savedVersion.getMimeType())
                .fileSizeBytes(savedVersion.getFileSize())
                .checksumSha256(savedVersion.getChecksumSha256())
                .createdAt(savedVersion.getUploadedAt())
                .build();

    }

}