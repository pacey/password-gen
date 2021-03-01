package com.github.pacey.passwordgen;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordGeneratorTest {

    private final Random random = new Random(850280435);

    @Test
    void generatesAnAlphabeticPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(false)
            .symbolic(false)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).isEqualTo("GJWmBNjsekxLePBp");
    }

    @Test
    void generatesANumericPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(false)
            .numeric(true)
            .symbolic(false)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).isEqualTo("6147329610571324");
    }

    @Test
    void generatesASymbolicPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(false)
            .numeric(false)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).isEqualTo(",+~'\"-#{*`=|?`%,");
    }

    @Test
    void generatesAMixedPasswordOfDesiredLength() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(true)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration, random);

        var password = passwordGenerator.generate();

        assertThat(password).isEqualTo("6JW{B\"j4)<1e5 (~");
    }

    @Test
    void generatedPasswordsAreRandom() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(true)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration);

        var passwords = Stream.generate(passwordGenerator::generate)
            .limit(1000)
            .collect(Collectors.toList());

        assertThat(passwords).doesNotHaveDuplicates();
    }

    @Test
    void weightingCanBeAppliedToDifferentClassesOfCharacters() {

        var configuration = Configuration.builder()
            .length(64)
            .alphabetic(true)
            .alphabeticWeight(.5F)
            .numeric(true)
            .numericWeight(.25F)
            .symbolic(false)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration);

        var passwords = Stream.generate(passwordGenerator::generate)
            .limit(1000)
            .collect(Collectors.toList());

        var averageAlphabetic = passwords.stream()
            .mapToDouble(password -> ((double) password.chars().filter(Character::isAlphabetic).count()) / password.length())
            .summaryStatistics()
            .getAverage();
        assertThat(averageAlphabetic).isCloseTo(.66D, Percentage.withPercentage(5));

        var averageNumeric = passwords.stream()
            .mapToDouble(password -> ((double) password.chars().filter(Character::isDigit).count()) / password.length())
            .summaryStatistics()
            .getAverage();
        assertThat(averageNumeric).isCloseTo(.33D, Percentage.withPercentage(5));
    }

    @Test
    @Disabled("For debugging only")
    void printSomeExamples() {

        var configuration = Configuration.builder()
            .length(16)
            .alphabetic(true)
            .numeric(true)
            .symbolic(true)
            .build();
        var passwordGenerator = new PasswordGenerator(configuration);

        Stream.generate(passwordGenerator::generate)
            .limit(10)
            .forEach(System.out::println);
    }
}
