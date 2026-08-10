package com.guyo.archive_system.storage.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.guyo.archive_system.storage.dto.StoredFileDto;
import com.guyo.archive_system.storage.util.ChecksumUtil;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    @Override
    public StoredFileDto store(MultipartFile file) {

        try {

            Path uploadDirectory = Paths.get(storageLocation);

            if (!Files.exists(uploadDirectory)) {

                Files.createDirectories(uploadDirectory);

            }

            String originalFileName = file.getOriginalFilename();

            String storedFileName =
                    UUID.randomUUID()
                            + "_"
                            + originalFileName;

            Path destination =
                    uploadDirectory.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String checksum;

            try (InputStream inputStream = Files.newInputStream(destination)) {

                checksum = ChecksumUtil.sha256(inputStream);

            }

            return StoredFileDto.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .filePath(destination.toString())
                    .mimeType(file.getContentType())
                    .fileSize(file.getSize())
                    .checksumSha256(checksum)
                    .build();

        }

        catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store uploaded file.",
                    e
            );

        }

    }

}