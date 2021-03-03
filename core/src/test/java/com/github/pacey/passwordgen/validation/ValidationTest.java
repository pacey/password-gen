package com.github.pacey.passwordgen.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.github.pacey.passwordgen.validation.FieldRequirement.MANDATORY;
import static com.github.pacey.passwordgen.validation.FieldRequirement.OPTIONAL;
import static com.github.pacey.passwordgen.validation.Validation.requireDefined;
import static com.github.pacey.passwordgen.validation.Validation.requireNonEmpty;
import static com.github.pacey.passwordgen.validation.Validation.requireRange;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Validation Tests")
class ValidationTest {

    @ParameterizedTest(name = "Validates {0} to not be null, throwing {1}")
    @MethodSource("validatesRequiredFieldsArguments")
    @DisplayName("requireDefined")
    void validatesRequiredFields(Object fieldValue, Exception expectedException) {

        if (expectedException == null) {
            var actual = requireDefined(fieldValue, "test field");
            assertThat(actual).isEqualTo(fieldValue);
        } else {
            assertThatThrownBy(() -> requireDefined(fieldValue, "test field"))
                .isExactlyInstanceOf(expectedException.getClass())
                .hasMessage(expectedException.getMessage());
        }
    }

    static Stream<Arguments> validatesRequiredFieldsArguments() {

        return Stream.of(
            Arguments.of("hello", null),
            Arguments.of(53, null),
            Arguments.of(null, new ValidationException("test field cannot be left undefined."))
        );
    }

    @ParameterizedTest(name = "Validates {0} to be between {1} and {2} ({3}), throwing {4}")
    @MethodSource("validatesRangeArguments")
    @DisplayName("requireRange")
    <T extends Comparable<T>> void validatesRange(T fieldValue, T min, T max, FieldRequirement requirement, Exception expectedException) {

        if (expectedException == null) {
            var actual = requireRange(fieldValue, "test field", min, max, requirement);
            assertThat(actual).isEqualTo(fieldValue);
        } else {
            assertThatThrownBy(() -> requireRange(fieldValue, "test field", min, max, requirement))
                .isExactlyInstanceOf(expectedException.getClass())
                .hasMessage(expectedException.getMessage());
        }
    }

    static Stream<Arguments> validatesRangeArguments() {

        return Stream.of(
            Arguments.of(null, 8, 12, MANDATORY, new ValidationException("test field cannot be left undefined.")),
            Arguments.of(10, 8, 12, MANDATORY, null),
            Arguments.of(10, 8, 12, OPTIONAL, null),
            Arguments.of(6, 8, 12, MANDATORY, new ValidationException("test field should fall in range 8-12.")),
            Arguments.of(14, 8, 12, MANDATORY, new ValidationException("test field should fall in range 8-12.")),
            Arguments.of(10, 8, null, MANDATORY, null),
            Arguments.of(6, 8, null, MANDATORY, new ValidationException("test field should be greater than or equal to 8.")),
            Arguments.of(6, null, 8, MANDATORY, null),
            Arguments.of(10, null, 8, MANDATORY, new ValidationException("test field should be less than or equal to 8."))
        );
    }

    @ParameterizedTest(name = "Validates {0} to not be empty ({1}), throwing {2}")
    @MethodSource("validatesCollectionNonEmptyArguments")
    @DisplayName("requireNonEmpty")
    <T extends Collection<?>> void validatesCollectionNonEmpty(T fieldValue, FieldRequirement requirement, Exception expectedException) {

        if (expectedException == null) {
            var actual = requireNonEmpty(fieldValue, "test field", requirement);
            assertThat(actual).isEqualTo(fieldValue);
        } else {
            assertThatThrownBy(() -> requireNonEmpty(fieldValue, "test field", requirement))
                .isExactlyInstanceOf(expectedException.getClass())
                .hasMessage(expectedException.getMessage());
        }
    }

    static Stream<Arguments> validatesCollectionNonEmptyArguments() {

        return Stream.of(
            Arguments.of(null, MANDATORY, new ValidationException("test field cannot be left undefined.")),
            Arguments.of(null, OPTIONAL, null),
            Arguments.of(List.of(), MANDATORY, new ValidationException("test field must not be empty.")),
            Arguments.of(List.of(1, 2, 3), MANDATORY, null)
        );
    }
}
