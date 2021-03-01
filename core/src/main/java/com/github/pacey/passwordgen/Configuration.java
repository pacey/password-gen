package com.github.pacey.passwordgen;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.github.pacey.passwordgen.Validation.FieldRequirement.MANDATORY;
import static com.github.pacey.passwordgen.Validation.requireRange;

/**
 * Configuration for password generation.
 */
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Configuration {

    @Builder.Default
    private Integer length = 8;
    @Builder.Default
    private boolean alphabetic = true;
    @Builder.Default
    private Float alphabeticWeight = .5F;
    @Builder.Default
    private boolean includeUppercase = true;
    @Builder.Default
    private boolean numeric = true;
    @Builder.Default
    private Float numericWeight = .5F;
    @Builder.Default
    private boolean symbolic = true;
    @Builder.Default
    private Float symbolicWeight = .5F;

    /**
     * Creates a new configuration.
     *
     * @param length           Length of the password, between 1 and 64 characters.
     * @param alphabetic       Whether to include alphabetic (a-z) characters.
     * @param alphabeticWeight Weight given to alphabetic characters between .1 and 1, against {@link #numericWeight} and {@link #symbolicWeight}.
     * @param includeUppercase Whether to include uppercase (A-Z) characters.
     * @param numeric          Whether to include numeric (0-9) characters.
     * @param numericWeight    Weight given to numeric characters between .1 and 1, against {@link #alphabeticWeight} and {@link #symbolicWeight}.
     * @param symbolic         Whether to include symbolic (!&% etc.) characters.
     * @param symbolicWeight   Weight given to symbolic characters between .1 and 1, against {@link #alphabeticWeight} and {@link #numericWeight}.
     */
    public Configuration(
        Integer length,
        boolean alphabetic,
        Float alphabeticWeight,
        boolean includeUppercase,
        boolean numeric,
        Float numericWeight,
        boolean symbolic,
        Float symbolicWeight
    ) {
        this.length = requireRange(length, "length", 1, 64, MANDATORY);
        this.alphabetic = alphabetic;
        this.alphabeticWeight = requireRange(alphabeticWeight, "alphabeticWeight", 0.1F, 1.0F, MANDATORY);
        this.includeUppercase = includeUppercase;
        this.numeric = numeric;
        this.numericWeight = requireRange(numericWeight, "numericWeight", 0.1F, 1.0F, MANDATORY);
        this.symbolic = symbolic;
        this.symbolicWeight = requireRange(symbolicWeight, "symbolicWeight", 0.1F, 1.0F, MANDATORY);
    }
}
