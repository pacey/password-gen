package com.github.pacey.passswordgen.api;

import com.github.pacey.passswordgen.fixture.PasswordFixture;
import com.github.pacey.passwordgen.api.ApiErrorResponse;
import com.github.pacey.passwordgen.api.PasswordResponse;
import com.github.pacey.passwordgen.app.provider.DateTimeProvider;
import com.github.pacey.passwordgen.app.provider.IdentifierProvider;
import com.github.pacey.passwordgen.app.provider.RandomProvider;
import io.micronaut.core.type.Argument;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Random;

import static com.github.pacey.passswordgen.fixture.PasswordFixture.FIXED_INSTANT;
import static com.github.pacey.passswordgen.fixture.PasswordFixture.FIXED_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MicronautTest
@DisplayName("PasswordApi Tests")
class PasswordApiTest {

    @Inject
    @Client("/")
    private HttpClient httpClient;

    @Test
    @DisplayName("Generates a strong password")
    void generatesAStrongPassword() {

        var fixture = PasswordFixture.strong();

        var actual = httpClient.toBlocking()
            .exchange(fixture.getHttpRequest(), PasswordResponse.class);

        var expected = fixture.getHttpResponse();
        assertThat((Object) actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getBody(PasswordResponse.class)).isEqualTo(expected.getBody());
    }

    @Test
    @DisplayName("Supports configuration by query string parameters")
    void supportsConfiguration() {

        var fixture = PasswordFixture.full();

        var actual = httpClient.toBlocking()
            .exchange(fixture.getHttpRequest(), PasswordResponse.class);

        var expected = fixture.getHttpResponse();
        assertThat((Object) actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getBody(PasswordResponse.class)).isEqualTo(expected.getBody());
    }

    @Test
    @DisplayName("Handles errors gracefully and consistently")
    void handlesErrorsGracefullyAndConsistently() {

        var fixture = PasswordFixture.invalid();

        var actual = catchThrowableOfType(
            () -> httpClient.toBlocking()
                .exchange(fixture.getHttpRequest(), Argument.of(PasswordResponse.class), Argument.of(ApiErrorResponse.class)),
            HttpClientResponseException.class
        ).getResponse();

        var expected = fixture.getHttpResponse();
        assertThat((Object) actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getBody(ApiErrorResponse.class)).isEqualTo(expected.getBody());
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

        // Can't be shared. Each test invocation needs a new random starting at the same seed.
        return () -> new Random(1485694);
    }
}
