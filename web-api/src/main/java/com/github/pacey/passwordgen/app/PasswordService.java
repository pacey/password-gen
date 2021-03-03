package com.github.pacey.passwordgen.app;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.PasswordGenerator;
import com.github.pacey.passwordgen.app.provider.RandomProvider;
import io.micrometer.core.annotation.Timed;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PasswordService {

    private final RandomProvider randomProvider;

    @Inject
    public PasswordService(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    @Timed(
        value = "password.generate", percentiles = { 0.1, 0.5, 0.9 },
        description = "Time taken to generate a password"
    )
    public String generatePassword(Configuration configuration) {
        return new PasswordGenerator(configuration, randomProvider.random()).generate();
    }
}
