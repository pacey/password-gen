package com.github.pacey.passwordgen.rule;

import java.util.List;
import java.util.function.IntPredicate;

/**
 * Class which can check if a character is similar in a configurable window.
 */
public class SimilarityRule implements PasswordRule {

    private static final Iterable<String> similarCharacters = List.of("1iIL!|", "0OQ", "nm", "NM", ";:", "\"'", "@a", "5S");
    private final int windowSize;

    /**
     * Creates a new similarity checker instance.
     *
     * @param windowSize Window size to look for similar characters in.
     */
    public SimilarityRule(int windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Check if the supplied string buffer contains similar characters in the window.
     *
     * @param buffer    String buffer to check in.
     * @param character Character to check.
     *
     * @return {@code true} if a similar character was found in the string buffer window, otherwise {@code false}.
     */
    @Override
    public boolean violates(StringBuffer buffer, Character character) {

        for (String similar : similarCharacters) {
            // Check if the proposed character appears in the similar
            if (similar.indexOf(character) > -1) {
                var windowTail = buffer.substring(Math.max(0, buffer.length() - windowSize));
                if (similar.chars().anyMatch(in(windowTail))) {
                    return true;
                }
            }
        }

        return false;
    }

    private IntPredicate in(String string) {
        return character -> string.indexOf(character) > -1;
    }
}
