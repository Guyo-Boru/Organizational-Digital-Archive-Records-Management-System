package com.guyo.archive_system.common.exception;

/**
 * Thrown when a request is well-formed but conflicts with the current state
 * of the resource (e.g. an invalid document workflow transition). Mapped to
 * HTTP 409 Conflict by {@link GlobalExceptionHandler}.
 */
public class InvalidStateException extends RuntimeException {

    public InvalidStateException(String message) {
        super(message);
    }
}
