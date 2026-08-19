package com.guyo.archive_system.user.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.audit.entity.AuditAction;
import com.guyo.archive_system.audit.entity.ResourceType;
import com.guyo.archive_system.audit.service.AuditLogService;
import com.guyo.archive_system.common.exception.InvalidStateException;
import com.guyo.archive_system.common.exception.ResourceNotFoundException;
import com.guyo.archive_system.user.dto.UserDto;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.mapper.UserMapper;
import com.guyo.archive_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuditLogService auditLogService;

    @Override
    public UserDto getById(UUID userSub) {

        User user = userRepository.findByUserSubAndDeletedAtIsNull(userSub)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userSub));

        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {

        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto deactivate(UUID userSub, UUID currentUserId) {

        User user = findActiveOrThrow(userSub);

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new InvalidStateException("User is already deactivated: " + userSub);
        }

        user.setIsActive(false);
        user.setUpdatedAt(OffsetDateTime.now());

        User saved = userRepository.save(user);

        auditLogService.log(
                currentUserId,
                AuditAction.UPDATE,
                ResourceType.USER,
                userSub,
                Map.of("field", "isActive", "value", false)
        );

        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public UserDto activate(UUID userSub, UUID currentUserId) {

        User user = findActiveOrThrow(userSub);

        if (Boolean.TRUE.equals(user.getIsActive())) {
            throw new InvalidStateException("User is already active: " + userSub);
        }

        user.setIsActive(true);
        user.setUpdatedAt(OffsetDateTime.now());

        User saved = userRepository.save(user);

        auditLogService.log(
                currentUserId,
                AuditAction.UPDATE,
                ResourceType.USER,
                userSub,
                Map.of("field", "isActive", "value", true)
        );

        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID userSub, UUID currentUserId) {

        User user = findActiveOrThrow(userSub);

        user.setDeletedAt(OffsetDateTime.now());
        user.setDeletedBy(currentUserId);

        userRepository.save(user);

        auditLogService.log(
                currentUserId,
                AuditAction.DELETE,
                ResourceType.USER,
                userSub,
                Map.of("email", user.getEmail())
        );
    }

    @Override
    @Transactional
    public UserDto restore(UUID userSub, UUID currentUserId) {

        User user = userRepository.findById(userSub)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userSub));

        if (user.getDeletedAt() == null) {
            throw new InvalidStateException("User is not deleted: " + userSub);
        }

        user.setDeletedAt(null);
        user.setDeletedBy(null);
        user.setUpdatedAt(OffsetDateTime.now());

        User restored = userRepository.save(user);

        auditLogService.log(
                currentUserId,
                AuditAction.RESTORE,
                ResourceType.USER,
                userSub,
                Map.of("email", user.getEmail())
        );

        return userMapper.toDto(restored);
    }

    private User findActiveOrThrow(UUID userSub) {

        return userRepository.findByUserSubAndDeletedAtIsNull(userSub)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userSub));
    }
}