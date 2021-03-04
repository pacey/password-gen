package com.github.pacey.passswordgen.fixture;

import com.github.pacey.passwordgen.api.ApiErrorResponse;
import com.github.pacey.passwordgen.api.PasswordRequest;
import com.github.pacey.passwordgen.api.PasswordResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.uri.UriBuilder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class PasswordFixture {

    public static final Instant FIXED_INSTANT = Instant.parse("2021-03-03T14:01:26Z");
    public static final UUID FIXED_UUID = UUID.fromString("720b84d5-ca16-4a98-9e0a-18e7f5fe874c");

    private final HttpRequest<PasswordRequest> httpRequest;
    private final HttpResponse<?> httpResponse;

    private PasswordFixture(
        HttpRequest<PasswordRequest> httpRequest,
        HttpResponse<?> httpResponse
    ) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public static PasswordFixture strong() {
        return new PasswordFixture(
            HttpRequest.GET("/api/password/strong"),
            HttpResponse.ok(new PasswordResponse("H6dc95GsV2ec'9B3", FIXED_INSTANT))
        );
    }

    public static PasswordFixture full() {
        return new PasswordFixture(
            HttpRequest.GET(
                UriBuilder.of("/api/password")
                    .queryParam("length", 64)
                    .queryParam("alphabetic", true)
                    .queryParam("includeUppercase", true)
                    .queryParam("alphabeticWeight", 1F)
                    .queryParam("numeric", true)
                    .queryParam("numericWeight", 1F)
                    .queryParam("symbolic", true)
                    .queryParam("symbolicWeight", .5F)
                    .queryParam("avoidRepetition", true)
                    .queryParam("avoidSimilar", true)
                    .build()
            ),
            HttpResponse.ok(new PasswordResponse("HOdc95Gsv2ec'9B3t(o0,pCywrx2l4EmB>Gbqy#73h9iEh4dMTyam,sV[l5b9o2w", FIXED_INSTANT))
        );
    }

    public static PasswordFixture invalid() {
        return new PasswordFixture(
            HttpRequest.GET(
                UriBuilder.of("/api/password")
                    .queryParam("length", 128)
                    .build()
            ),
            HttpResponse.badRequest(new ApiErrorResponse(
                FIXED_UUID,
                FIXED_INSTANT,
                HttpStatus.BAD_REQUEST,
                "length should fall in range 1-64.",
                "/api/password",
                "GET"
            ))
        );
    }
}
