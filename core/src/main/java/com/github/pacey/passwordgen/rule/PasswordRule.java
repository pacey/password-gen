package com.github.pacey.passwordgen.rule;

/**
 * Interface for classes that need to check passwords against a rule.
 */
@FunctionalInterface
public interface PasswordRule {

    /**
     * Check if the given character violates the rule for the current password.
     *
     * @param buffer    Current password as a string buffer.
     * @param character Character to check.
     *
     * @return {@code true} if the character violates the rule and should not be accepted, otherwise {@code false}.
     */
    boolean violates(StringBuffer buffer, Character character);
}
