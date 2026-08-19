package com.guyo.archive_system.user.service;

import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.user.dto.UserDto;

public interface UserService {

    UserDto getById(UUID userSub);

    List<UserDto> getAll();

    /**
     * Admin-toggled deactivation (isActive = false). The user still exists
     * and is still visible to admins/auditors — this only blocks the
     * account from being used (enforce the isActive check wherever
     * authentication/authorization happens), it does not hide the record.
     * Distinct from {@link #delete}.
     */
    UserDto deactivate(UUID userSub, UUID currentUserId);

    /** Reverses {@link #deactivate} (isActive = true). */
    UserDto activate(UUID userSub, UUID currentUserId);

    /**
     * Soft-deletes the user record itself (sets deletedAt/deletedBy). Use
     * this for actual removal — e.g. duplicate accounts, offboarding tied
     * to record retention — not for routine "this person shouldn't log in
     * right now", which is {@link #deactivate}.
     */
    void delete(UUID userSub, UUID currentUserId);

    /** Reverses a soft delete performed via {@link #delete}. */
    UserDto restore(UUID userSub, UUID currentUserId);

}