package com.github.pacey.passwordgen;

import java.util.Arrays;

/**
 * Helper class to store different character classes and some basic utility methods.
 */
class Characters {

    private static final char[] alphabetic = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] alphabeticUppercase = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final char[] numeric = "0123456789".toCharArray();
    private static final char[] symbolic = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray();

    private Characters() {
    }

    /**
     * Static factory to reference alphabetic characters.
     *
     * @return char array of alphabetic characters.
     */
    static char[] alphabetic() {
        return alphabetic.clone();
    }

    /**
     * Static factory to reference alphabetic uppercase characters.
     *
     * @return char array of alphabetic uppercase characters.
     */
    static char[] alphabeticUppercase() {
        return alphabeticUppercase.clone();
    }

    /**
     * Static factory to reference numeric characters.
     *
     * @return char array of numeric characters.
     */
    static char[] numeric() {
        return numeric.clone();
    }

    /**
     * Static factory to reference symbolic characters.
     *
     * @return char array of symbolic characters.
     */
    static char[] symbolic() {
        return symbolic.clone();
    }

    /**
     * Combines the two arrays into a new third array, leaving the supplied arrays untouched.
     *
     * @param first  First array.
     * @param second Second array.
     *
     * @return Third array containing all elements from the first and second.
     */
    static char[] combine(char[] first, char[] second) {

        char[] combined = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }
}
