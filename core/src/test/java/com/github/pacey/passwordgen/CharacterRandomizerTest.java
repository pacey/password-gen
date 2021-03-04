package com.github.pacey.passwordgen;

import com.github.pacey.passwordgen.validation.ValidationException;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CharacterRandomizer Tests")
class CharacterRandomizerTest {

    private final Random random = new Random(46186377);

    @Test
    void throwsExceptionIfNoCharacterSetsHaveBeenAdded() {

        assertThatThrownBy(() -> new CharacterRandomizer(random, List.of()))
            .isInstanceOf(ValidationException.class)
            .hasMessage("weighted strings must not be empty.");
    }

    @Test
    @DisplayName("Can randomize from a single CharSequence")
    void canRandomizeFromASingleCharSequence() {

        var charSequence = "abcd";
        var characterRandomizer = new CharacterRandomizer(
            random,
            List.of(new WeightedString(1F, charSequence))
        );

        var characterStream = Stream.generate(characterRandomizer::next)
            .limit(10_000);

        assertThat(characterStream).allSatisfy(character ->
            assertThat(charSequence).contains(character.toString())
        );
    }

    @Test
    @DisplayName("Weighting can be applied to different CharSequences")
    void weightingCanBeAppliedToDifferentCharSequences() {

        var charSequenceRandomizer = new CharacterRandomizer(
            random,
            List.of(
                new WeightedString(1F, "abc"),
                new WeightedString(2F, "def")
            )
        );

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
