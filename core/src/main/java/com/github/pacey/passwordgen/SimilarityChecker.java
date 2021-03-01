package com.github.pacey.passwordgen;

import java.util.Arrays;

/**
 * Class which can check if a character is similar in a configurable window.
 */
class SimilarityChecker implements PasswordChecker {

    private final int windowSize;

    /**
     * Creates a new similarity checker instance.
     *
     * @param windowSize Window size to look for similar characters in.
     */
    SimilarityChecker(int windowSize) {
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
    public boolean check(StringBuffer buffer, char character) {

        var similarCharacters = Characters.similar();
        for (char[] similarArray : similarCharacters) {
            // Check if the proposed character appears in the similar
            if (Arrays.binarySearch(similarArray, character) > -1) {
                var windowTail = buffer.substring(Math.max(0, buffer.length() - windowSize));
                for (int index = 0; index < windowTail.length(); index++) {
                    // Check if the character in the tail also appears in the similar
                    if (Arrays.binarySearch(similarArray, windowTail.charAt(index)) > -1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
