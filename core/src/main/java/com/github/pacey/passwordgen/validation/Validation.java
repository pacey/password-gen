package com.github.pacey.passwordgen.validation;

import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;

/**
 * Helper class for performing validation.
 */
public class Validation {

    /**
     * Creates a new instance. Has been made private to hide the default constructor.
     */
    private Validation() {
    }

    /**
     * Require the given field to not be null.
     *
     * @param fieldValue Field value.
     * @param fieldName  Field name, used for formatting exceptions.
     * @param <T>        Type of the field.
     *
     * @return Field value, should the validation pass.
     * @throws ValidationException should the validation fail.
     */
    public static <T> T requireDefined(T fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new ValidationException(fieldName + " cannot be left undefined.");
        }
        return fieldValue;
    }

    /**
     * Require the given field to be in a particular range.
     *
     * @param fieldValue   Field value.
     * @param fieldName    Field name, used for formatting exceptions.
     * @param minInclusive Minimum (inclusive) value the field can have.
     * @param maxInclusive Maximum (inclusive) value the field can have.
     * @param requirement  Requirement of the field to be mandatory or optional.
     * @param <T>          Type of the field.
     *
     * @return Field value, should the validation pass.
     * @throws ValidationException should the validation fail.
     */
    public static <T extends Comparable<T>> T requireRange(
        T fieldValue,
        @NonNull String fieldName,
        T minInclusive,
        T maxInclusive,
        @NonNull FieldRequirement requirement
    ) {
        if (fieldValue == null) {
            if (requirement == FieldRequirement.MANDATORY) {
                throw new ValidationException(fieldName + " cannot be left undefined.");
            }
            return null;
        }
        if (minInclusive != null && maxInclusive != null) {
            if (fieldValue.compareTo(minInclusive) < 0 || fieldValue.compareTo(maxInclusive) > 0) {
                throw new ValidationException(fieldName + " should fall in range " + minInclusive + "-" + maxInclusive + ".");
            }
        } else if (minInclusive != null) {
            if (fieldValue.compareTo(minInclusive) < 0) {
                throw new ValidationException(fieldName + " should be greater than or equal to " + minInclusive + ".");
            }
        } else if (maxInclusive != null) {
            if (fieldValue.compareTo(maxInclusive) > 0) {
                throw new ValidationException(fieldName + " should be less than or equal to " + maxInclusive + ".");
            }
        } else {
            throw new ValidationException("Both lower and upper limits on a range validation cannot be null simultaneously.");
        }
        return fieldValue;
    }

    /**
     * Require the given field to not be empty.
     *
     * @param fieldValue  Field value.
     * @param fieldName   Field name, used for formatting exceptions.
     * @param requirement Requirement of the field to be mandatory or optional.
     * @param <C>         Type of the collection.
     *
     * @return Field value, should the validation pass.
     * @throws ValidationException should the validation fail.
     */
    public static <C extends Collection<?>> C requireNonEmpty(C fieldValue, String fieldName, FieldRequirement requirement) {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(requirement);

        if (requirement == FieldRequirement.OPTIONAL && fieldValue == null) {
            return null;
        } else if (fieldValue == null) {
            throw new ValidationException(fieldName + " cannot be left undefined.");
        } else if (fieldValue.isEmpty()) {
            throw new ValidationException(fieldName + " must not be empty.");
        } else {
            return fieldValue;
        }
    }

    public static String requireNonBlank(String fieldValue, String fieldName, FieldRequirement requirement) {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(requirement);

        if (requirement == FieldRequirement.OPTIONAL && fieldValue == null) {
            return null;
        } else if (fieldValue == null) {
            throw new ValidationException(fieldName + " cannot be left undefined.");
        } else if (fieldValue.isBlank()) {
            throw new ValidationException(fieldName + " must not be blank.");
        } else {
            return fieldValue;
        }

    }
}
