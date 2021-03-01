package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.app.PasswordService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Status;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.function.Consumer;

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
    public String generatePassword(
        @Nullable @QueryValue Integer length,
        @Nullable @QueryValue Boolean alphabetic,
        @Nullable @QueryValue Boolean numeric,
        @Nullable @QueryValue Boolean symbolic
    ) {

        var configurationBuilder = Configuration.builder();

        setIfProvided(length, configurationBuilder::length);
        setIfProvided(alphabetic, configurationBuilder::alphabetic);
        setIfProvided(numeric, configurationBuilder::numeric);
        setIfProvided(symbolic, configurationBuilder::symbolic);

        return passwordService.generatePassword(configurationBuilder.build());
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

    private static <T> void setIfProvided(T value, Consumer<T> setter) {

        if (value != null) {
            setter.accept(value);
        }
    }

}
