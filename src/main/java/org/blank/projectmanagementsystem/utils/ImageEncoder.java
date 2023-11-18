package org.blank.projectmanagementsystem.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageEncoder {
    public static String encodeToBase64(byte[] data) {
        return (data != null) ? Base64.getEncoder().encodeToString(data) : null;
    }
}
