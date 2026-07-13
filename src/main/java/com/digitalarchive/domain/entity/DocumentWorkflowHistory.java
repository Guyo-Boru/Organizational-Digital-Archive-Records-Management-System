package com.digitalarchive.domain.entity;

import com.digitalarchive.domain.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

/** Every workflow transition is written here — see slide 11. */
@Entity
@Table(name = "document_workflow_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentWorkflowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_id")
    private Long workflowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "from_status")
    private DocumentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "to_status", nullable = false)
    private DocumentStatus toStatus;

    @Column(name = "changed_by", nullable = false)
    private String changedBy;

    // Required on rejection per slide 7's workflow rules
    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "changed_at", insertable = false, updatable = false)
    private OffsetDateTime changedAt;
}
