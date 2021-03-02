package com.github.pacey.passwordgen;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CharacterRandomizer Tests")
class CharacterRandomizerTest {

    private final Random random = new Random(46186377);

    @Test
    @DisplayName("Can randomize from a single CharSequence")
    void canRandomizeFromASingleCharSequence() {

        var charSequence = "abcd";
        var characterRandomizer = new CharacterRandomizer(random)
            .add(1F, charSequence);

        var characterStream = Stream.generate(characterRandomizer::next)
            .limit(10_000);

        assertThat(characterStream).allSatisfy(character ->
            assertThat(charSequence).contains(character.toString())
        );
    }

    @Test
    @DisplayName("Weighting can be applied to different CharSequences")
    void weightingCanBeAppliedToDifferentCharSequences() {

        var charSequenceRandomizer = new CharacterRandomizer(random)
            .add(1F, "abc")
            .add(2F, "def");

        var randomString = charSequenceRandomizer.stream()
            .limit(10_000)
            .map(Object::toString)
            .collect(Collectors.joining());

        var averageFirstBucket = ((double) randomString.chars()
            .filter(character -> "abc".indexOf(character) > -1)
            .count()) / randomString.length();
        assertThat(averageFirstBucket).isCloseTo(.33D, Percentage.withPercentage(5));

        var averageSecondBucket = ((double) randomString.chars()
            .filter(character -> "def".indexOf(character) > -1)
            .count()) / randomString.length();
        assertThat(averageSecondBucket).isCloseTo(.66D, Percentage.withPercentage(5));
    }
}
