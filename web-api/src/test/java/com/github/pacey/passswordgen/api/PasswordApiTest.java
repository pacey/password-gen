package com.github.pacey.passswordgen.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
class PasswordApiTest {

    @Inject
    EmbeddedServer embeddedServer;

    private BlockingHttpClient httpClient;

    @BeforeAll
    void beforeAll() {
        httpClient = HttpClient.create(embeddedServer.getURL()).toBlocking();
    }

    @Test
    void generatesAStrongPassword() {

        var password = httpClient.retrieve(HttpRequest.GET("/api/password/strong"), String.class);

        assertThat(password)
            .hasSize(16);
    }

    @Test
    void supportsConfiguration() {

        var password = httpClient.retrieve(HttpRequest.GET("/api/password?length=64"), String.class);

        assertThat(password)
            .hasSize(64);
    }
}
