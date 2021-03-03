package com.github.pacey.passwordgen.api;

import io.micronaut.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@AllArgsConstructor
@Schema(title = "Error Response")
public class ApiErrorResponse {

    @Schema(description = "Identifier of the error. Useful for debugging.")
    UUID id;
    @Schema(description = "Time the error occurred.")
    Instant timestamp;
    @Schema(
        description = "Http status code.",
        example = "400"
    )
    int status;
    @Schema(
        description = "Http status string.",
        example = "Bad Request"
    )
    String error;
    @Schema(
        description = "Error message.",
        example = "1 does not equal 2."
    )
    String message;
    @Schema(
        description = "Path that was called.",
        example = "/api/path"
    )
    String path;
    @Schema(
        description = "Http method that was used.",
        example = "GET"
    )
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
