package com.github.pacey.passwordgen.rule;

/**
 * Class which can check if a character has been repeated in a configurable window.
 */
public class RepetitionRule implements PasswordRule {

    private final int windowSize;

    /**
     * Creates a new repetition checker instance.
     *
     * @param windowSize Window size to look for repeated characters in.
     */
    public RepetitionRule(int windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Check if the supplied string buffer contains the given character in the window.
     *
     * @param buffer    String buffer to check in.
     * @param character Character to check.
     *
     * @return {@code true} if the character was found in the string buffer window, otherwise {@code false}.
     */
    @Override
    public boolean violates(StringBuffer buffer, Character character) {

        var tailWindow = buffer.substring(Math.max(buffer.length() - windowSize, 0));

        return tailWindow.indexOf(character) > -1;
    }
}
