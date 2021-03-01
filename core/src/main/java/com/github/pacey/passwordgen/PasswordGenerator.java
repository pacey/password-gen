package com.github.pacey.passwordgen;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import static com.github.pacey.passwordgen.Characters.alphabetic;
import static com.github.pacey.passwordgen.Characters.alphabeticUppercase;
import static com.github.pacey.passwordgen.Characters.combine;
import static com.github.pacey.passwordgen.Characters.numeric;
import static com.github.pacey.passwordgen.Characters.symbolic;

public class PasswordGenerator {

    private final Random random;
    private final NavigableMap<Float, char[]> weightedCharacters = new TreeMap<>();
    private final Float totalWeight;
    private final RepetitionChecker repetitionChecker;
    private final Configuration configuration;

    public PasswordGenerator(Configuration configuration) {
        this(configuration, new Random());
    }

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
        this.repetitionChecker = new RepetitionChecker(3);
        this.random = random;
    }

    public String generate() {

        StringBuffer passwordBuffer = new StringBuffer(configuration.getLength());
        for (int index = 0; index < configuration.getLength(); index++) {
            var chars = weightedCharacters.higherEntry(random.nextFloat() * totalWeight).getValue();
            passwordBuffer.append(generateNextCharacter(passwordBuffer, chars));
        }

        return passwordBuffer.toString();
    }

    private char generateNextCharacter(StringBuffer currentPassword, char[] chars) {
        char nextCharacter;
        do {
            nextCharacter = randomCharacterFrom(chars);
        } while (repetitionChecker.contains(currentPassword, nextCharacter));

        return nextCharacter;
    }

    private char randomCharacterFrom(char[] chars) {
        return chars[random.nextInt(chars.length)];
    }
}
