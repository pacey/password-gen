package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.app.provider.DateTimeProvider;
import com.github.pacey.passwordgen.app.PasswordService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.RequestBean;

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
    public HttpResponse<PasswordResponse> generatePassword(@RequestBean PasswordRequest passwordRequest) {

        var password = passwordService.generatePassword(passwordRequest.toConfiguration());
        return HttpResponse.ok(new PasswordResponse(password, dateTimeProvider.now()));
    }

    @Get("/strong")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<PasswordResponse> generateStrongPassword() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .includeUppercase(true)
            .numeric(true)
            .symbolic(true)
            .build();

        var password = passwordService.generatePassword(configuration);
        return HttpResponse.ok(new PasswordResponse(password, dateTimeProvider.now()));
    }
}
