package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day02.Game;
import xyz.barbieri.adventofcode.Day02.Round;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void parse_game_and_rounds() {
        Game game = Day02.parse("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        assertThat(game.id()).isEqualTo(1);

        assertThat(game.rounds()).hasSize(3);
        assertThat(game.rounds().get(0).red()).isEqualTo(4);
        assertThat(game.rounds().get(0).green()).isEqualTo(0);
        assertThat(game.rounds().get(0).blue()).isEqualTo(3);

        assertThat(game.rounds().get(1).red()).isEqualTo(1);
        assertThat(game.rounds().get(1).green()).isEqualTo(2);
        assertThat(game.rounds().get(1).blue()).isEqualTo(6);

        assertThat(game.rounds().get(2).red()).isEqualTo(0);
        assertThat(game.rounds().get(2).green()).isEqualTo(2);
        assertThat(game.rounds().get(2).blue()).isEqualTo(0);
    }

    @Test
    void check_possible_round() {
        assertThat(new Round(0, 0, 0).isPossible()).isTrue();
        assertThat(new Round(1, 2, 3).isPossible()).isTrue();
        assertThat(new Round(12, 13, 14).isPossible()).isTrue();

        assertThat(new Round(13, 13, 14).isPossible()).isFalse();
        assertThat(new Round(12, 14, 14).isPossible()).isFalse();
        assertThat(new Round(12, 13, 15).isPossible()).isFalse();
    }

}
