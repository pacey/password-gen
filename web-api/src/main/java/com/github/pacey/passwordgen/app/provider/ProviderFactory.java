package com.github.pacey.passwordgen.app.provider;

import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Factory
public class ProviderFactory {

    @Singleton
    DateTimeProvider dateTimeProvider() {
        return Instant::now;
    }

    @Singleton
    IdentifierProvider identifierProvider() {
        return UUID::randomUUID;
    }

    @Singleton
    RandomProvider randomProvider() {
        var random = new Random();
        return () -> random;
    }
}
