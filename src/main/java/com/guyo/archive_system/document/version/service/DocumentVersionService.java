package com.guyo.archive_system.document.version.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.guyo.archive_system.document.version.dto.UploadDocumentVersionResponse;

public interface DocumentVersionService {

    UploadDocumentVersionResponse uploadVersion(
            UUID documentId,
            MultipartFile file,
            UUID currentUserId
    );

}