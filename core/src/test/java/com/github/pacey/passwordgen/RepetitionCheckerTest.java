package com.github.pacey.passwordgen;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RepetitionCheckerTest {

    @ParameterizedTest(name = "Last {2} characters of ''{0}'' contains ''{1}'' is {3}")
    @MethodSource("containsCharacterInBufferWindowArguments")
    void containsCharacterInBufferWindow(StringBuffer buffer, char character, int windowSize, boolean expected) {

        var actual = new RepetitionChecker(windowSize).contains(buffer, character);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> containsCharacterInBufferWindowArguments() {
        return Stream.of(
            Arguments.of(new StringBuffer(), 'a', 1, false),
            Arguments.of(new StringBuffer("a"), 'a', 1, true),
            Arguments.of(new StringBuffer("ab"), 'a', 1, false),

            Arguments.of(new StringBuffer(), 'a', 2, false),
            Arguments.of(new StringBuffer("a"), 'a', 2, true),
            Arguments.of(new StringBuffer("ab"), 'a', 2, true),
            Arguments.of(new StringBuffer("abc"), 'a', 2, false),

            Arguments.of(new StringBuffer(), 'a', 3, false),
            Arguments.of(new StringBuffer("a"), 'a', 3, true),
            Arguments.of(new StringBuffer("ab"), 'a', 3, true),
            Arguments.of(new StringBuffer("abc"), 'a', 3, true),
            Arguments.of(new StringBuffer("abcd"), 'a', 3, false)
        );
    }
}
