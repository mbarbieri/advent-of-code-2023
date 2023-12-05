package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    String card = "Card   1: 75 68 35 36 86 83 30 11 14 59 | 86 25 63 57 91 68 14 72 32 36 74 66 44 30 28 11 35 75 34 55 83 69 56 38";

    String test = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """.stripIndent();

    @Test
    void winning_numbers() {
        assertThat(Day04.winningNumbers(card)).containsExactlyInAnyOrder(11, 14, 30, 35, 36, 68, 75, 83, 86, 59);
    }

    @Test
    void card_numbers() {
        assertThat(Day04.cardNumbers(card)).containsExactlyInAnyOrder(86, 25, 63, 57, 91, 68, 14, 72, 32, 36, 74, 66, 44, 30, 28, 11, 35, 75, 34, 55, 83, 69, 56, 38);
    }

    @Test
    void extracted_numbers() {
        assertThat(Day04.extractedNumbers(card)).containsExactlyInAnyOrder(75, 68, 35, 36, 86, 83, 30, 11, 14);
    }

    @Test
    void points() {
        assertThat(Day04.points(Set.of())).isEqualTo(0);
        assertThat(Day04.points(Set.of(1))).isEqualTo(1);
        assertThat(Day04.points(Set.of(1,2))).isEqualTo(2);
        assertThat(Day04.points(Set.of(1,2,3,4))).isEqualTo(8);
    }

    @Test
    void winning_cards_count() {
        assertThat(Day04.winningCardsCount(Arrays.asList(test.split("\n")))).isEqualTo(30);
    }
}
