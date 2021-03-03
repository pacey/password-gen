package com.github.pacey.passswordgen.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
@DisplayName("PasswordApi Tests")
class PasswordApiTest {

    @Inject
    EmbeddedServer embeddedServer;

    private BlockingHttpClient httpClient;

    @BeforeAll
    void beforeAll() {
        httpClient = HttpClient.create(embeddedServer.getURL()).toBlocking();
    }

    @Test
    @DisplayName("Generates a strong password")
    void generatesAStrongPassword() {

        var password = httpClient.retrieve(HttpRequest.GET("/api/password/strong"), String.class);

        assertThat(password)
            .hasSize(16);
    }

    @Test
    @DisplayName("Supports configuration by query string parameters")
    void supportsConfiguration() {

        var httpRequest = HttpRequest.GET("/api/password")
            .uri(uriBuilder -> uriBuilder.queryParam("length", 64)
                .queryParam("alphabetic", true)
                .queryParam("includeUppercase", true)
                .queryParam("alphabeticWeight", 1F)
                .queryParam("numeric", true)
                .queryParam("numericWeight", 1F)
                .queryParam("symbolic", true)
                .queryParam("symbolicWeight", .5F)
                .queryParam("avoidRepetition", true)
                .queryParam("avoidSimilar", true)
            );

        var password = httpClient.retrieve(httpRequest, String.class);

        assertThat(password)
            .hasSize(64);
    }
}
