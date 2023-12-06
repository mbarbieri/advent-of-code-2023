package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day06.Race;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {

    @Test
    void first_race() {
        Race race = new Race(7, 9);
        assertThat(race.winning()).containsExactly(2L, 3L, 4L, 5L);
    }

    @Test
    void second_race() {
        Race race = new Race(15, 40);
        assertThat(race.winning()).containsExactly(4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);
    }

    @Test
    void third_race() {
        Race race = new Race(30, 200);
        assertThat(race.winning()).containsExactly(11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L);
    }

}
