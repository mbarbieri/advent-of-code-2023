package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day09.History;
import xyz.barbieri.adventofcode.Day09.Sequence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    @Test
    void parse_history() {
        History history = Day09.parseHistory("0 3 6 9 12 15");
        assertThat(history.values()).containsExactly(0, 3, 6, 9, 12, 15);
    }

    @Test
    void next_sequence() {
        Sequence sequence = new Sequence(List.of(0, 3, 6, 9, 12, 15));
        assertThat(sequence.next().values()).containsExactly(3, 3, 3, 3, 3);
    }

    @Test
    void next_sequence_zeroes() {
        Sequence sequence = new Sequence(List.of(3, 3, 3, 3, 3));
        assertThat(sequence.next().values()).containsExactly(0, 0, 0, 0);
    }

    @Test
    void check_zero() {
        Sequence sequence = new Sequence(List.of(0, 0, 0, 0));
        assertThat(sequence.isZero()).isTrue();
    }

    @Test
    void next_value_in_history_1() {
        History history = Day09.parseHistory("0 3 6 9 12 15");
        assertThat(history.nextValue()).isEqualTo(18);
    }

    @Test
    void next_value_in_history_2() {
        History history = Day09.parseHistory("1 3 6 10 15 21");
        assertThat(history.nextValue()).isEqualTo(28);
    }

    @Test
    void next_value_in_history_3() {
        History history = Day09.parseHistory("10 13 16 21 30 45");
        assertThat(history.nextValue()).isEqualTo(68);
    }

    @Test
    void previous_value() {
        History history = Day09.parseHistory("10 13 16 21 30 45");
        assertThat(history.previousValue()).isEqualTo(5);
    }
}
