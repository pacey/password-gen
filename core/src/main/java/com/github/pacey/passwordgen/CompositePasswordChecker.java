package com.github.pacey.passwordgen;

import java.util.List;

import static com.github.pacey.passwordgen.Validation.FieldRequirement.MANDATORY;
import static com.github.pacey.passwordgen.Validation.requireNonEmpty;

public class CompositePasswordChecker implements PasswordChecker {

    private final List<PasswordChecker> passwordCheckers;

    CompositePasswordChecker(List<PasswordChecker> passwordCheckers) {
        this.passwordCheckers = requireNonEmpty(passwordCheckers, "password checker", MANDATORY);
    }

    CompositePasswordChecker(PasswordChecker... passwordChecker) {
        this(List.of(passwordChecker));
    }

    @Override
    public boolean check(StringBuffer buffer, char character) {
        return passwordCheckers.stream()
            .anyMatch(passwordChecker -> passwordChecker.check(buffer, character));
    }
}
