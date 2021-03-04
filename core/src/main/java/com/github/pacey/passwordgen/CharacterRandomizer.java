package com.github.pacey.passwordgen;

import com.github.pacey.passwordgen.validation.Validation;

import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.github.pacey.passwordgen.validation.FieldRequirement.MANDATORY;

/**
 * Class that can pick random characters from a series of weighted strings.
 */
class CharacterRandomizer {

    private final Random random;
    private final NavigableMap<Float, String> weightedStringMap = new TreeMap<>();
    private float totalWeight = 0F;

    /**
     * Creates a new instance with a given random and weighted strings.
     *
     * @param random          Random to use.
     * @param weightedStrings Weighted strings to randomly selected characters from.
     */
    CharacterRandomizer(Random random, List<WeightedString> weightedStrings) {
        this.random = random;
        Validation.requireNonEmpty(weightedStrings, "weighted strings", MANDATORY);
        for (WeightedString weightedString : weightedStrings) {
            weightedStringMap.put(totalWeight += weightedString.getWeight(), weightedString.getString());
        }
    }

    /**
     * Creates a new stream of random characters.
     *
     * @return Stream of random characters.
     */
    Stream<Character> stream() {
        return Stream.generate(this::next);
    }

    /**
     * Get the next random character.
     *
     * @return Random character.
     */
    Character next() {
        var characters = weightedStringMap.higherEntry(random.nextFloat() * totalWeight).getValue();
        return characters.charAt(random.nextInt(characters.length()));
    }
}
