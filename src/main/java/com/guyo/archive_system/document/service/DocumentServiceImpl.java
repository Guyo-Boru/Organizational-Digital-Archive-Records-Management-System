package com.guyo.archive_system.document.service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;
import com.guyo.archive_system.audit.service.AuditLogService;
import com.guyo.archive_system.common.exception.InvalidStateException;
import com.guyo.archive_system.common.exception.ResourceNotFoundException;
import com.guyo.archive_system.document.dto.DocumentDto;
import com.guyo.archive_system.document.dto.DocumentSearchRequest;
import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.mapper.DocumentMapper;
import com.guyo.archive_system.document.repository.DocumentRepository;
import com.guyo.archive_system.document.repository.DocumentSortProperties;
import com.guyo.archive_system.document.repository.DocumentSpecification;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    private final DocumentMapper documentMapper;

    private final AuditLogService auditLogService;

    @Override
    public DocumentDto getById(UUID documentId) {

        Document document = findActiveOrThrow(documentId);

        return documentMapper.toDto(document);
    }

    @Override
    public DocumentDto getByReferenceNumber(String referenceNumber) {

        Document document =
                documentRepository.findByReferenceNumber(referenceNumber)
                        .filter(doc -> doc.getDeletedAt() == null)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Document not found: " + referenceNumber
                                )
                        );

        return documentMapper.toDto(document);
    }

    @Override
    public Page<DocumentDto> getAll(Pageable pageable) {

        return search(
                DocumentSearchRequest.builder().build(),
                pageable,
                null
        );
    }

    @Override
    public Page<DocumentDto> search(
            DocumentSearchRequest request,
            Pageable pageable,
            UUID currentUserId) {

        DocumentSortProperties.validate(pageable.getSort());

        /*
         * TODO(security): includeDeleted currently has no authorization
         * gate — any authenticated caller can set it and read soft-deleted
         * documents. This audit entry makes that visible after the fact
         * but does not prevent it; replace/augment with a role check
         * (e.g. @PreAuthorize("hasRole('RECORDS_MANAGER')")) once a role
         * model exists.
         */
        if (request != null && request.isIncludeDeleted()) {

            auditLogService.log(
                    currentUserId,
                    AuditAction.VIEW,
                    ResourceType.DOCUMENT,
                    null,
                    Map.of("includeDeleted", true)
            );
        }

        return documentRepository
                .findAll(
                        DocumentSpecification.search(request),
                        pageable
                )
                .map(documentMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID documentId, UUID currentUserId) {

        Document document = findActiveOrThrow(documentId);

        document.setDeletedAt(OffsetDateTime.now());
        document.setDeletedBy(currentUserId);

        documentRepository.save(document);

        auditLogService.log(
                currentUserId,
                AuditAction.DELETE,
                ResourceType.DOCUMENT,
                documentId,
                Map.of("referenceNumber", document.getReferenceNumber())
        );
    }

    @Override
    @Transactional
    public DocumentDto restore(UUID documentId, UUID currentUserId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Document not found: " + documentId
                        )
                );

        if (document.getDeletedAt() == null) {

            throw new InvalidStateException(
                    "Document is not deleted: " + documentId
            );
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + currentUserId
                        )
                );

        document.setDeletedAt(null);
        document.setDeletedBy(null);
        document.setUpdatedAt(OffsetDateTime.now());
        document.setUpdatedBy(user);

        Document restored = documentRepository.save(document);

        auditLogService.log(
                currentUserId,
                AuditAction.RESTORE,
                ResourceType.DOCUMENT,
                documentId,
                Map.of("referenceNumber", document.getReferenceNumber())
        );

        return documentMapper.toDto(restored);
    }

    private Document findActiveOrThrow(UUID documentId) {

        return documentRepository.findById(documentId)
                .filter(doc -> doc.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Document not found: " + documentId
                        )
                );
    }
}
