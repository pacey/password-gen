package com.github.pacey.passwordgen;

class RepetitionChecker {

    private final int windowSize;

    public RepetitionChecker(int windowSize) {
        this.windowSize = windowSize;
    }

    boolean contains(StringBuffer buffer, char character) {

        var tailWindow = buffer.substring(Math.max(buffer.length() - windowSize, 0));

        return tailWindow.indexOf(character) > -1;
    }
}
