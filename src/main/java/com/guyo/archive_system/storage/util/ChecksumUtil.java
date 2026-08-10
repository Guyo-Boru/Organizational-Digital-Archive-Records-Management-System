package com.guyo.archive_system.storage.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ChecksumUtil {

    private ChecksumUtil() {
    }

    public static String sha256(InputStream inputStream)
            throws IOException {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[8192];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                digest.update(buffer, 0, bytesRead);

            }

            byte[] hash = digest.digest();

            StringBuilder builder = new StringBuilder();

            for (byte b : hash) {

                builder.append(String.format("%02x", b));

            }

            return builder.toString();

        }

        catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("SHA-256 algorithm not available.", e);

        }

    }

}