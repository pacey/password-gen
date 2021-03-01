package com.github.pacey.passwordgen;

import java.util.Arrays;

public class Characters {

    private static final char[] alphabetic = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] alphabeticUppercase = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final char[] numeric = "0123456789".toCharArray();
    private static final char[] symbolic = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray();

    private Characters() {
    }

    public static char[] alphabetic() {
        return alphabetic;
    }

    public static char[] alphabeticUppercase() {
        return alphabeticUppercase;
    }

    public static char[] numeric() {
        return numeric;
    }

    public static char[] symbolic() {
        return symbolic;
    }

    public static char[] combine(char[] first, char[] second) {

        char[] combined = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }
}
