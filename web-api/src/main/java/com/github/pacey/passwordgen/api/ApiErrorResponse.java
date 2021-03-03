package com.github.pacey.passwordgen.api;

import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@AllArgsConstructor
public class ApiErrorResponse {

    UUID id;
    Instant timestamp;
    int status;
    String error;
    String message;
    String path;
    String method;

    public ApiErrorResponse(UUID id, Instant timestamp, HttpStatus httpStatus, String message, String path, String method) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = httpStatus.getCode();
        this.error = httpStatus.getReason();
        this.message = message;
        this.path = path;
        this.method = method;
    }
}
