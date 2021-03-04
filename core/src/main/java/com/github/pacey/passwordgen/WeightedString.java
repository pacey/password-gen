package com.github.pacey.passwordgen;

import lombok.Value;

import static com.github.pacey.passwordgen.validation.FieldRequirement.MANDATORY;
import static com.github.pacey.passwordgen.validation.Validation.requireNonBlank;
import static com.github.pacey.passwordgen.validation.Validation.requireRange;

@Value
class WeightedString {

    Float weight;
    String string;

    WeightedString(Float weight, String string) {
        this.weight = requireRange(weight, "weight", 0.1F, null, MANDATORY);
        this.string = requireNonBlank(string, "string", MANDATORY);
    }
}
