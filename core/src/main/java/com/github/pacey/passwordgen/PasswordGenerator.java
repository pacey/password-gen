package com.github.pacey.passwordgen;

import com.github.pacey.passwordgen.rule.CompositePasswordRule;
import com.github.pacey.passwordgen.rule.PasswordRule;
import com.github.pacey.passwordgen.rule.RepetitionRule;
import com.github.pacey.passwordgen.rule.SimilarityRule;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

/**
 * A basic configurable password generator.
 */
public class PasswordGenerator {

    private static final String alphabetic = "abcdefghijklmnopqrstuvwxyz";
    private static final String alphabeticUppercase = alphabetic.toUpperCase();
    private static final String numeric = "0123456789";
    private static final String symbolic = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    private final CharacterRandomizer characterRandomizer;
    private final PasswordRule compositeChecker;
    private final Configuration configuration;

    /**
     * Creates a new instance of a password generator.
     *
     * @param configuration Configuration for the generator.
     */
    public PasswordGenerator(Configuration configuration) {
        this(configuration, new Random());
    }

    /**
     * Creates a new instance of a password generator.
     *
     * @param configuration Configuration for the generator.
     * @param random        Random instance, mainly used for testing/repeatable results.
     */
    public PasswordGenerator(Configuration configuration, Random random) {
        this.configuration = configuration;
        this.characterRandomizer = createCharacterRandomizer(configuration, random);
        this.compositeChecker = createPasswordChecker(configuration);
    }

    private static CharacterRandomizer createCharacterRandomizer(Configuration configuration, Random random) {
        CharacterRandomizer characterRandomizer = new CharacterRandomizer(random);

        if (configuration.isAlphabetic() && configuration.isIncludeUppercase()) {
            characterRandomizer.add(configuration.getAlphabeticWeight(), alphabetic + alphabeticUppercase);
        } else if (configuration.isAlphabetic()) {
            characterRandomizer.add(configuration.getAlphabeticWeight(), alphabetic);
        }
        if (configuration.isNumeric()) {
            characterRandomizer.add(configuration.getNumericWeight(), numeric);
        }
        if (configuration.isSymbolic()) {
            characterRandomizer.add(configuration.getSymbolicWeight(), symbolic);
        }

        return characterRandomizer;
    }

    private static PasswordRule createPasswordChecker(Configuration configuration) {

        var passwordCheckers = new ArrayList<PasswordRule>();
        if (configuration.isAvoidRepetition()) {
            passwordCheckers.add(new RepetitionRule(3));
        }
        if (configuration.isAvoidSimilar()) {
            passwordCheckers.add(new SimilarityRule(1));
        }

        return passwordCheckers.isEmpty()
            ? null
            : new CompositePasswordRule(passwordCheckers);

    }

    /**
     * Generates a new password based on the generators configuration.
     *
     * @return String representation of the password.
     */
    public String generate() {

        StringBuffer passwordBuffer = new StringBuffer(configuration.getLength());
        for (int index = 0; index < configuration.getLength(); index++) {
            passwordBuffer.append(generateNextCharacter(passwordBuffer));
        }

        return passwordBuffer.toString();
    }

    /**
     * Creates a new infinite stream which is capable of generating passwords based on the generators configuration.
     *
     * @return Stream of string passwords.
     */
    public Stream<String> stream() {
        return Stream.generate(this::generate);
    }

    private char generateNextCharacter(StringBuffer currentPassword) {

        char nextCharacter;
        do {
            nextCharacter = characterRandomizer.next();
        } while (compositeChecker != null && compositeChecker.violates(currentPassword, nextCharacter));

        return nextCharacter;
    }
}
