package com.github.pacey.passwordgen;

import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;

public class Validation {

    private Validation() {
    }

    public static <T> T requireDefined(T fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(fieldName + " cannot be left undefined.");
        }
        return fieldValue;
    }

    public static <T extends Comparable<T>> T requireRange(
        T fieldValue,
        @NonNull String fieldName,
        T minInclusive,
        T maxInclusive,
        @NonNull FieldRequirement requirement
    ) {
        if (fieldValue == null) {
            if (requirement == FieldRequirement.MANDATORY) {
                throw new IllegalArgumentException(fieldName + " cannot be left undefined.");
            }
            return null;
        }
        if (minInclusive != null && maxInclusive != null) {
            if (fieldValue.compareTo(minInclusive) < 0 || fieldValue.compareTo(maxInclusive) > 0) {
                throw new IllegalArgumentException(fieldName + " should fall in range " + minInclusive + "-" + maxInclusive + ".");
            }
        } else if (minInclusive != null) {
            if (fieldValue.compareTo(minInclusive) < 0) {
                throw new IllegalArgumentException(fieldName + " should be greater than or equal to " + minInclusive + ".");
            }
        } else if (maxInclusive != null) {
            if (fieldValue.compareTo(maxInclusive) > 0) {
                throw new IllegalArgumentException(fieldName + " should be less than or equal to " + maxInclusive + ".");
            }
        } else {
            throw new IllegalArgumentException("Both lower and upper limits on a range validation cannot be null simultaneously.");
        }
        return fieldValue;
    }

    public static <C extends Collection<?>> C requireNonEmpty(C fieldValue, String fieldName, FieldRequirement requirement) {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(requirement);
        if (requirement == FieldRequirement.OPTIONAL && fieldValue == null) {
            return null;
        } else if (fieldValue == null) {
            throw new IllegalArgumentException(fieldName + " cannot be left undefined.");
        } else if (fieldValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        } else {
            return fieldValue;
        }
    }

    public enum FieldRequirement {

        /** The field has to have a defined value and must be considered invalid if left undefined. */
        MANDATORY,

        /** The field is optional and will be validated only if it has a defined value. Undefined fields will be considered valid. */
        OPTIONAL
    }
}
