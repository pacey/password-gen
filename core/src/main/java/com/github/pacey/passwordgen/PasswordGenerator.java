package com.github.pacey.passwordgen;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.github.pacey.passwordgen.Characters.alphabetic;
import static com.github.pacey.passwordgen.Characters.alphabeticUppercase;
import static com.github.pacey.passwordgen.Characters.combine;
import static com.github.pacey.passwordgen.Characters.numeric;
import static com.github.pacey.passwordgen.Characters.symbolic;

/**
 * A basic configurable password generator.
 */
public class PasswordGenerator {

    private final Random random;
    private final NavigableMap<Float, char[]> weightedCharacters = new TreeMap<>();
    private final Float totalWeight;
    private final PasswordChecker compositeChecker;
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
        var totalWeight = 0F;
        if (configuration.isAlphabetic() && configuration.isIncludeUppercase()) {
            this.weightedCharacters.put(totalWeight += configuration.getAlphabeticWeight(), combine(alphabetic(), alphabeticUppercase()));
        } else if (configuration.isAlphabetic()) {
            this.weightedCharacters.put(totalWeight += configuration.getAlphabeticWeight(), alphabetic());
        }
        if (configuration.isNumeric()) {
            this.weightedCharacters.put(totalWeight += configuration.getNumericWeight(), numeric());
        }
        if (configuration.isSymbolic()) {
            this.weightedCharacters.put(totalWeight += configuration.getSymbolicWeight(), symbolic());
        }
        this.totalWeight = totalWeight;
        compositeChecker = createPasswordChecker(configuration);
        this.random = random;
    }

    private static PasswordChecker createPasswordChecker(Configuration configuration) {

        var passwordCheckers = new ArrayList<PasswordChecker>();
        if (configuration.isAvoidRepetition()) {
            passwordCheckers.add(new RepetitionChecker(3));
        }
        if (configuration.isAvoidSimilar()) {
            passwordCheckers.add(new SimilarityChecker(3));
        }

        return passwordCheckers.isEmpty()
            ? null
            : new CompositePasswordChecker(passwordCheckers);

    }

    /**
     * Generates a new password based on the generators configuration.
     *
     * @return String representation of the password.
     */
    public String generate() {

        StringBuffer passwordBuffer = new StringBuffer(configuration.getLength());
        for (int index = 0; index < configuration.getLength(); index++) {
            var chars = weightedCharacters.higherEntry(random.nextFloat() * totalWeight).getValue();
            passwordBuffer.append(generateNextCharacter(passwordBuffer, chars));
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

    private char generateNextCharacter(StringBuffer currentPassword, char[] chars) {
        char nextCharacter = randomCharacterFrom(chars);

        if (compositeChecker == null) {
            return nextCharacter;
        }

        while (compositeChecker.check(currentPassword, nextCharacter)) {
            nextCharacter = randomCharacterFrom(chars);
        }
        return nextCharacter;
    }

    private char randomCharacterFrom(char[] chars) {
        return chars[random.nextInt(chars.length)];
    }
}
