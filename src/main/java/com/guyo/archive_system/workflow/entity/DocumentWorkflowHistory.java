package com.guyo.archive_system.workflow.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.enums.DocumentStatus;
import com.guyo.archive_system.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document_workflow_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentWorkflowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "workflow_id")
    private UUID workflowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
            name = "from_status",
            columnDefinition = "document_status"
    )
    private DocumentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
            name = "to_status",
            nullable = false,
            columnDefinition = "document_status"
    )
    private DocumentStatus toStatus;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @PrePersist
    public void prePersist() {
        if (changedAt == null) {
            changedAt = OffsetDateTime.now();
        }
    }

}