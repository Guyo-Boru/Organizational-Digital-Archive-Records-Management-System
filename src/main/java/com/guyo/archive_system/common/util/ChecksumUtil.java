package com.guyo.archive_system.common.util;

import java.io.InputStream;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class ChecksumUtil {

    public String sha256(InputStream inputStream) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[8192];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                digest.update(buffer, 0, bytesRead);

            }

            byte[] hash = digest.digest();

            StringBuilder hex = new StringBuilder();

            for (byte b : hash) {

                hex.append(
                        String.format("%02x", b)
                );

            }

            return hex.toString();

        }

        catch (Exception e) {

            throw new RuntimeException(
                    "Failed to calculate SHA-256 checksum.",
                    e
            );

        }

    }

}