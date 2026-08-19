package com.guyo.archive_system.note.enums;

/**
 * Categorizes a {@code DocumentNote}.
 * <p>
 * {@link #isUserCreatable()} distinguishes types a client may set directly
 * (via {@code CreateDocumentNoteRequest}/{@code UpdateDocumentNoteRequest})
 * from types the system attaches automatically as a side effect of another
 * action, so that clients cannot forge system-authored entries.
 */
public enum NoteType {

    /** Free-form note added by a user. */
    GENERAL(true),

    /** Feedback left while a document is under review. */
    REVIEW(true),

    /** Reason given when a document is rejected in the workflow. */
    REJECTION_REASON(true),

    /** Clarification or correction requested from the document owner. */
    CLARIFICATION_REQUEST(true),

    /** Internal note visible only to reviewers/administrators, not the submitter. */
    INTERNAL(true),

    /**
     * System-generated note (e.g. auto-attached on a workflow transition
     * or version upload). Never accepted from a client request body.
     */
    SYSTEM(false);

    private final boolean userCreatable;

    NoteType(boolean userCreatable) {
        this.userCreatable = userCreatable;
    }

    public boolean isUserCreatable() {
        return userCreatable;
    }
}
