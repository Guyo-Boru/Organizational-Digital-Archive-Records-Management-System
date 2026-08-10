package com.guyo.archive_system.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredFileDto {

    private String originalFileName;

    private String storedFileName;

    private String filePath;

    private String mimeType;

    private Long fileSize;

    private String checksumSha256;

}