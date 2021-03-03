package com.github.pacey.passswordgen.api;

import com.github.pacey.passwordgen.api.ApiErrorResponse;
import com.github.pacey.passwordgen.api.PasswordResponse;
import com.github.pacey.passwordgen.app.provider.DateTimeProvider;
import com.github.pacey.passwordgen.app.provider.IdentifierProvider;
import com.github.pacey.passwordgen.app.provider.RandomProvider;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MicronautTest
@TestInstance(PER_CLASS)
@DisplayName("PasswordApi Tests")
class PasswordApiTest {

    public static final Instant FIXED_INSTANT = Instant.parse("2021-03-03T14:01:26Z");
    public static final UUID FIXED_UUID = UUID.fromString("720b84d5-ca16-4a98-9e0a-18e7f5fe874c");
    @Inject
    @Client("/")
    private HttpClient httpClient;

    @Test
    @DisplayName("Generates a strong password")
    void generatesAStrongPassword() {

        var httpResponse = httpClient.toBlocking()
            .exchange(HttpRequest.GET("/api/password/strong"), PasswordResponse.class);

        assertThat((Object) httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody(PasswordResponse.class))
            .hasValue(
                new PasswordResponse("56dc958sv2=eC2'9", FIXED_INSTANT)
            );
    }

    @Test
    @DisplayName("Supports configuration by query string parameters")
    void supportsConfiguration() {

        var httpRequest = HttpRequest.GET(
            UriBuilder.of("/api/password?{}")
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
        );

        var httpResponse = httpClient.toBlocking()
            .exchange(httpRequest, PasswordResponse.class);

        assertThat((Object) httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody(PasswordResponse.class))
            .hasValue(
                new PasswordResponse("56dc95gsv2eC'913T(O,PCYwRX2l4emB>gbqY#73H/i6H4d27y5Am,S3[L75b9O{", FIXED_INSTANT)
            );
    }

    @Test
    @DisplayName("Handles errors gracefully and consistently")
    void handlesErrorsGracefullyAndConsistently() {

        var httpRequest = HttpRequest.GET(
            UriBuilder.of("/api/password")
                .queryParam("length", 128)
                .build()
        );

        var responseException = catchThrowableOfType(
            () -> httpClient.toBlocking()
                .exchange(httpRequest, Argument.of(PasswordResponse.class), Argument.of(ApiErrorResponse.class)),
            HttpClientResponseException.class
        );

        assertThat((Object) responseException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseException.getResponse().getBody(ApiErrorResponse.class))
            .hasValue(
                new ApiErrorResponse(
                    FIXED_UUID,
                    FIXED_INSTANT,
                    HttpStatus.BAD_REQUEST,
                    "length should fall in range 1-64.",
                    "/api/password",
                    "GET"
                )
            );
    }

    @MockBean(DateTimeProvider.class)
    DateTimeProvider mockDateTimeProvider() {

        var dateTimeProvider = mock(DateTimeProvider.class);
        given(dateTimeProvider.now()).willReturn(FIXED_INSTANT);
        return dateTimeProvider;
    }

    @MockBean(IdentifierProvider.class)
    IdentifierProvider mockIdentifierProvider() {

        var identifierProvider = mock(IdentifierProvider.class);
        given(identifierProvider.identifier()).willReturn(FIXED_UUID);
        return identifierProvider;
    }

    @MockBean(RandomProvider.class)
    RandomProvider fixedSeedRandomProvider() {

        var random = new Random(1485694);
        return () -> random;
    }
}
