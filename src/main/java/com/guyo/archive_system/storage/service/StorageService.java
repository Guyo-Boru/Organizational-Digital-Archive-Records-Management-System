package com.guyo.archive_system.storage.service;

import org.springframework.web.multipart.MultipartFile;

import com.guyo.archive_system.storage.dto.StoredFileDto;

public interface StorageService {

    StoredFileDto store(MultipartFile file);

}