package com.github.pacey.passwordgen;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimilarityCheckerTest {

    @ParameterizedTest(name = "Last {2} characters of ''{0}'' similar to ''{1}'' is {3}")
    @MethodSource("containsSimilarCharacterInBufferWindowArguments")
    void containsSimilarCharacterInBufferWindow(StringBuffer buffer, char character, int windowSize, boolean expected) {

        var actual = new SimilarityChecker(windowSize).check(buffer, character);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> containsSimilarCharacterInBufferWindowArguments() {

        return Stream.of(
            Arguments.of(new StringBuffer("1"), 'I', 1, true),
            Arguments.of(new StringBuffer("1N"), 'I', 1, false),
            Arguments.of(new StringBuffer("0"), 'O', 1, true),
            Arguments.of(new StringBuffer("0N"), 'O', 1, false),
            Arguments.of(new StringBuffer("1N"), 'I', 2, true),
            Arguments.of(new StringBuffer("1NK"), 'I', 2, false),
            Arguments.of(new StringBuffer("0N"), 'O', 2, true),
            Arguments.of(new StringBuffer("0NK"), '0', 2, false)
        );
    }
}
