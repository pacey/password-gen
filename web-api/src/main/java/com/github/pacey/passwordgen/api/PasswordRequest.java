package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Introspected
@Value
public class PasswordRequest {

    @Nullable
    @QueryValue
    @Parameter(description = "Length of the password, between 1 and 64 characters.")
    Integer length;
    @Nullable
    @QueryValue
    @Parameter(description = "Whether to include alphabetic (a-z) characters.")
    Boolean alphabetic;
    @Nullable
    @QueryValue
    @Parameter(description = "Whether to include uppercase (A-Z) characters.")
    Boolean uppercase;
    @Nullable
    @QueryValue
    @Parameter(description = "Weight given to alphabetic characters between .1 and 1.")
    Float alphabeticWeight;
    @Nullable
    @QueryValue
    @Parameter(description = "Whether to include numeric (0-9) characters.")
    Boolean numeric;
    @Nullable
    @QueryValue
    @Parameter(description = "Weight given to numeric characters between .1 and 1.")
    Float numericWeight;
    @Nullable
    @QueryValue
    @Parameter(description = "Whether to include symbolic (!&% etc.) characters.")
    Boolean symbolic;
    @Nullable
    @QueryValue
    @Parameter(description = "Weight given to symbolic characters between .1 and 1.")
    Float symbolicWeight;
    @Nullable
    @QueryValue
    @Parameter(description = "Prevents the same characters from appearing near each other.")
    Boolean avoidRepetition;
    @Nullable
    @QueryValue
    @Parameter(description = "Prevents similar characters (1!I etc.) from appearing near each other.")
    Boolean avoidSimilar;

    Optional<Integer> getLength() {
        return Optional.ofNullable(length);
    }

    Optional<Boolean> getAlphabetic() {
        return Optional.ofNullable(alphabetic);
    }

    Optional<Boolean> getUppercase() {
        return Optional.ofNullable(uppercase);
    }

    Optional<Float> getAlphabeticWeight() {
        return Optional.ofNullable(alphabeticWeight);
    }

    Optional<Boolean> getNumeric() {
        return Optional.ofNullable(numeric);
    }

    Optional<Float> getNumericWeight() {
        return Optional.ofNullable(numericWeight);
    }

    Optional<Boolean> getSymbolic() {
        return Optional.ofNullable(symbolic);
    }

    Optional<Float> getSymbolicWeight() {
        return Optional.ofNullable(symbolicWeight);
    }

    Optional<Boolean> getAvoidRepetition() {
        return Optional.ofNullable(avoidRepetition);
    }

    Optional<Boolean> getAvoidSimilar() {
        return Optional.ofNullable(avoidSimilar);
    }

    Configuration toConfiguration() {

        var configurationBuilder = Configuration.builder();
        getLength().ifPresent(configurationBuilder::length);
        getAlphabetic().ifPresent(configurationBuilder::alphabetic);
        getUppercase().ifPresent(configurationBuilder::uppercase);
        getAlphabeticWeight().ifPresent(configurationBuilder::alphabeticWeight);
        getNumeric().ifPresent(configurationBuilder::numeric);
        getNumericWeight().ifPresent(configurationBuilder::numericWeight);
        getSymbolic().ifPresent(configurationBuilder::symbolic);
        getSymbolicWeight().ifPresent(configurationBuilder::symbolicWeight);
        getAvoidRepetition().ifPresent(configurationBuilder::avoidRepetition);
        getAvoidSimilar().ifPresent(configurationBuilder::avoidSimilar);
        return configurationBuilder.build();
    }
}
