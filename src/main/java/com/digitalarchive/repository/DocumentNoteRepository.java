package com.digitalarchive.repository;

import com.digitalarchive.domain.entity.DocumentNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentNoteRepository extends JpaRepository<DocumentNote, Long> {
    List<DocumentNote> findByDocument_DocumentIdOrderByCreatedAtDesc(Long documentID);

}