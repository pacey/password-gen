package com.github.pacey.passwordgen;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Class that can pick random characters from a series of weighted character sequences.
 */
class CharacterRandomizer {

    private final Random random;
    private final NavigableMap<Float, CharSequence> weightedCharSequence = new TreeMap<>();
    private float totalWeight = 0F;

    /**
     * Creates a nwe instance with a new Random.
     */
    CharacterRandomizer() {
        this(new Random());
    }

    /**
     * Creates a new instance with a given Random.
     *
     * @param random Random to use.
     */
    CharacterRandomizer(Random random) {
        this.random = random;
    }

    /**
     * Add a new character sequence with the given weight.
     *
     * @param weight       Weight to apply to character sequence.
     * @param charSequence Character sequence.
     *
     * @return Instance of {@link this} to allow method chaining.
     */
    CharacterRandomizer add(float weight, CharSequence charSequence) {

        weightedCharSequence.put(totalWeight += weight, charSequence);
        return this;
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

        var characters = weightedCharSequence.higherEntry(random.nextFloat() * totalWeight).getValue();
        return characters.charAt(random.nextInt(characters.length()));
    }
}
