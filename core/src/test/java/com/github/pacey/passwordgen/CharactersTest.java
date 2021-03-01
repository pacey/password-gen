package com.github.pacey.passwordgen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharactersTest {

    @Test
    void combinesCharArrays() {

        char[] first = new char[]{ 'a', 'b', 'c' };
        char[] second = new char[]{ 'd', 'e', 'f' };

        var third = Characters.combine(first, second);

        assertThat(third).containsExactly('a', 'b', 'c', 'd', 'e', 'f');

        assertThat(first).containsExactly('a', 'b', 'c');
        assertThat(second).containsExactly('d', 'e', 'f');
    }
}
