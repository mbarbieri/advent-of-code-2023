package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void find_two_numbers() {
        assertThat(Day01.firstAndLastFromString("10")).isEqualTo(10);
    }

    @Test
    void find_first_and_last_number() {
        assertThat(Day01.firstAndLastFromString("2abc0")).isEqualTo(20);
        assertThat(Day01.firstAndLastFromString("xxxx2abc0xxxx")).isEqualTo(20);
        assertThat(Day01.firstAndLastFromString("xxx2x234abc45xxx0xxxx")).isEqualTo(20);
    }

    @Test
    void replace_spelled_numbers() {
        assertThat(Day01.replaceSpelledNumbers("twothree")).isEqualTo("t2ot3e");
    }

    @Test
    void find_numbers_after_replace() {
        assertThat(Day01.firstAndLastFromString(
                Day01.replaceSpelledNumbers("aa7aaa6five")
        )).isEqualTo(75);

        // tricky case
        assertThat(Day01.firstAndLastFromString(
                Day01.replaceSpelledNumbers("nineightxxx2")
        )).isEqualTo(92);
    }
}
