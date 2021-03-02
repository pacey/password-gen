package com.github.pacey.passwordgen.rule;

import java.util.List;

import static com.github.pacey.passwordgen.validation.FieldRequirement.MANDATORY;
import static com.github.pacey.passwordgen.validation.Validation.requireNonEmpty;

public class CompositePasswordRule implements PasswordRule {

    private final List<PasswordRule> passwordRules;

    public CompositePasswordRule(List<PasswordRule> passwordRules) {
        this.passwordRules = requireNonEmpty(passwordRules, "password checker", MANDATORY);
    }

    public CompositePasswordRule(PasswordRule... passwordRule) {
        this(List.of(passwordRule));
    }

    @Override
    public boolean violates(StringBuffer buffer, Character character) {
        return passwordRules.stream()
            .anyMatch(passwordRule -> passwordRule.violates(buffer, character));
    }
}
