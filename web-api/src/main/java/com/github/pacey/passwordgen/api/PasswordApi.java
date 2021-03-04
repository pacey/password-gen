package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.app.PasswordService;
import com.github.pacey.passwordgen.app.provider.DateTimeProvider;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.RequestBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.inject.Inject;

@Controller("/api/password")
public class PasswordApi {

    private final PasswordService passwordService;
    private final DateTimeProvider dateTimeProvider;

    @Inject
    public PasswordApi(PasswordService passwordService, DateTimeProvider dateTimeProvider) {
        this.passwordService = passwordService;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Generate a password.",
        description = "Generates a password base on the supplied configuration. Sensible defaults are provided."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Password was generated successfully.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PasswordResponse.class)
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "A validation error occurred. Please check your configuration.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ApiErrorResponse.class)
        )
    )
    public HttpResponse<PasswordResponse> generatePassword(@RequestBean PasswordRequest passwordRequest) {

        var password = passwordService.generatePassword(passwordRequest.toConfiguration());
        return HttpResponse.ok(new PasswordResponse(password, dateTimeProvider.now()));
    }

    @Get("/strong")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Generate a strong password.",
        description = "Generates a strong password with mixed content and 16 characters in length."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Password was generated successfully.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PasswordResponse.class)
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "A validation error occurred. Please check your configuration.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ApiErrorResponse.class)
        )
    )
    public HttpResponse<PasswordResponse> generateStrongPassword() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .uppercase(true)
            .numeric(true)
            .symbolic(true)
            .build();

        var password = passwordService.generatePassword(configuration);
        return HttpResponse.ok(new PasswordResponse(password, dateTimeProvider.now()));
    }
}
