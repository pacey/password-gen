package com.github.pacey.passwordgen;

/**
 * Interface for classes that need to check passwords.
 */
@FunctionalInterface
interface PasswordChecker {

    /**
     * Check if the given character is acceptable in the current password.
     *
     * @param buffer    Current password as a string buffer.
     * @param character Character to check.
     *
     * @return {@code true} if the character is allowed, otherwise {@code false}.
     */
    boolean check(StringBuffer buffer, char character);
}
