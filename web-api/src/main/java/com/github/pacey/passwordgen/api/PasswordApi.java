package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.app.PasswordService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.RequestBean;
import io.micronaut.http.annotation.Status;

import javax.inject.Inject;

@Controller("/api/password")
public class PasswordApi {

    private final PasswordService passwordService;

    @Inject
    public PasswordApi(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    @Status(HttpStatus.OK)
    public String generatePassword(@RequestBean PasswordRequest passwordRequest) {

        return passwordService.generatePassword(passwordRequest.toConfiguration());
    }

    @Get("/strong")
    @Produces(MediaType.TEXT_PLAIN)
    @Status(HttpStatus.OK)
    public String generateStrongPassword() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .includeUppercase(true)
            .numeric(true)
            .symbolic(true)
            .build();

        return passwordService.generatePassword(configuration);
    }
}
