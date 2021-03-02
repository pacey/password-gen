package com.github.pacey.passwordgen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PasswordGenerator Tests")
class PasswordGeneratorTest {

    private final Random random = new Random(850280435);

    @Test
    @DisplayName("Generates alphabetic passwords of a desired length")
    void generatesAnAlphabeticPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(false)
            .symbolic(false)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).matches("[a-zA-Z]{16}");
    }

    @Test
    @DisplayName("Generates numeric passwords of a desired length")
    void generatesANumericPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(false)
            .numeric(true)
            .symbolic(false)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).matches("[0-9]{16}");
    }

    @Test
    @DisplayName("Generates symbolic passwords of a desired length")
    void generatesASymbolicPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(false)
            .numeric(false)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).matches("[ !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{16}");
    }

    @Test
    @DisplayName("Generates mixed passwords of a desired length")
    void generatesAMixedPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(true)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).isEqualTo("6JW{B\"j4)<145 (~");
    }

    @Test
    @DisplayName("Generated passwords are random")
    void generatedPasswordsAreRandom() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(true)
            .symbolic(true)
            .build();

        var passwords = new PasswordGenerator(configuration).stream()
            .limit(10_000)
            .collect(Collectors.toList());

        assertThat(passwords).doesNotHaveDuplicates();
    }
}
