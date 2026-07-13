package com.digitalarchive.repository;

import com.digitalarchive.domain.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    List<DocumentVersion> findByDocument_DocumentIDOrderByVersionNumberDesc(Long documentId);
}