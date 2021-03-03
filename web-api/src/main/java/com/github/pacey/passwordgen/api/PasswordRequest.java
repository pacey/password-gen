package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.Configuration;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Introspected
@Value
class PasswordRequest {

    @Nullable
    @QueryValue
    Integer length;
    @Nullable
    @QueryValue
    Boolean alphabetic;
    @Nullable
    @QueryValue
    Boolean includeUppercase;
    @Nullable
    @QueryValue
    Float alphabeticWeight;
    @Nullable
    @QueryValue
    Boolean numeric;
    @Nullable
    @QueryValue
    Float numericWeight;
    @Nullable
    @QueryValue
    Boolean symbolic;
    @Nullable
    @QueryValue
    Float symbolicWeight;
    @Nullable
    @QueryValue
    Boolean avoidRepetition;
    @Nullable
    @QueryValue
    Boolean avoidSimilar;

    Optional<Integer> getLength() {
        return Optional.ofNullable(length);
    }

    Optional<Boolean> getAlphabetic() {
        return Optional.ofNullable(alphabetic);
    }

    Optional<Boolean> getIncludeUppercase() {
        return Optional.ofNullable(includeUppercase);
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
        getIncludeUppercase().ifPresent(configurationBuilder::includeUppercase);
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
