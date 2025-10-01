package com.labanca.fibonacci.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    Integer status,
    String message,
    String path
) {
    public ErrorResponse(Integer status, String message, String path) {
        this(LocalDateTime.now(), status, message, path);
    }
}
