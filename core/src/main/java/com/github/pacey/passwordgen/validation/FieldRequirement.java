package com.github.pacey.passwordgen.validation;

/**
 * Requirement of the field being validated.
 */
public enum FieldRequirement {

    /** The field has to have a defined value and must be considered invalid if left undefined. */
    MANDATORY,

    /** The field is optional and will be validated only if it has a defined value. Undefined fields will be considered valid. */
    OPTIONAL
}
