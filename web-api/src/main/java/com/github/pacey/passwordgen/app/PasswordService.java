package com.github.pacey.passwordgen.app;

import com.github.pacey.passwordgen.Configuration;
import com.github.pacey.passwordgen.PasswordGenerator;
import io.micrometer.core.annotation.Timed;

import javax.inject.Singleton;

@Singleton
public class PasswordService {

    @Timed(
        value = "password.generate", percentiles = { 0.1, 0.5, 0.9 },
        description = "Time taken to generate a password"
    )
    public String generatePassword(Configuration configuration) {
        return new PasswordGenerator(configuration).generate();
    }
}
