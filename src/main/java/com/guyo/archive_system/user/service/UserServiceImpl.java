package com.guyo.archive_system.user.service;

import com.guyo.archive_system.user.dto.UserDto;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.mapper.UserMapper;
import com.guyo.archive_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(UUID userSub) {

        User user = userRepository.findById(userSub)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}