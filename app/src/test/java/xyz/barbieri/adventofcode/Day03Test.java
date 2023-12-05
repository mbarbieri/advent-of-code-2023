package xyz.barbieri.adventofcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day03.Coord;

import java.util.Set;

class Day03Test {

    String test = """
            ...#..
            $..5..
            23....
            ...8..
            ...123
            ..*...
            .10...""".stripIndent();

    @Test
    void symbol_coordinates() {
        Assertions.assertThat(Day03.symbolsCoordinates(test))
                .containsExactlyInAnyOrder(new Coord(3, 0), new Coord(0, 1), new Coord(2, 5));
    }

    @Test
    void find_valid_numbers() {
        Assertions.assertThat(Day03.findValidNumbers(test, Set.of(new Coord(3, 0), new Coord(0, 1), new Coord(2, 5))))
                .containsExactlyInAnyOrder(5, 23, 123, 10);
    }
}
