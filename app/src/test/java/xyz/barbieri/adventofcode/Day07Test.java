package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day07.Hand;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.barbieri.adventofcode.Day07.Hand.FIVE_OF_A_KIND;
import static xyz.barbieri.adventofcode.Day07.Hand.FOUR_OF_A_KIND;
import static xyz.barbieri.adventofcode.Day07.Hand.FULL_HOUSE;
import static xyz.barbieri.adventofcode.Day07.Hand.HIGH_CARD;
import static xyz.barbieri.adventofcode.Day07.Hand.ONE_PAIR;
import static xyz.barbieri.adventofcode.Day07.Hand.THREE_OF_A_KIND;
import static xyz.barbieri.adventofcode.Day07.Hand.TWO_PAIRS;

class Day07Test {

    private String test = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""".stripIndent();

    @Test
    void compare_smaller_hands_1() {
        assertThat(new Hand("32T3K")).isLessThan(new Hand("T55J5"));
        assertThat(new Hand("32T3K")).isLessThan(new Hand("KK677"));
        assertThat(new Hand("32T3K")).isLessThan(new Hand("KTJJT"));
        assertThat(new Hand("32T3K")).isLessThan(new Hand("QQQJA"));
    }

    @Test
    void compare_two_pairs_1() {
        assertThat(new Hand("KK677")).isGreaterThan(new Hand("KTJJT"));
    }

    @Test
    void hand_rank_1() {
        assertThat(new Hand("23456").handRank()).isEqualTo(HIGH_CARD);
        assertThat(new Hand("32T3K").handRank()).isEqualTo(ONE_PAIR);
        assertThat(new Hand("KK677").handRank()).isEqualTo(TWO_PAIRS);
        assertThat(new Hand("JJJ23").handRank()).isEqualTo(THREE_OF_A_KIND);
        assertThat(new Hand("KKKQQ").handRank()).isEqualTo(FULL_HOUSE);
        assertThat(new Hand("KKKKQ").handRank()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(new Hand("KKKKK").handRank()).isEqualTo(FIVE_OF_A_KIND);
        assertThat(new Hand("22222").handRank()).isEqualTo(FIVE_OF_A_KIND);
    }

    @Test
    void parse_hand() {
        assertThat(Hand.of("32T3K 765")).isEqualTo(new Hand("32T3K", 765));
    }

    @Test
    void hand_rank_2() {
        // with a joker an high card is a pair
        assertThat(new Hand("2345J", true).handRank()).isEqualTo(ONE_PAIR);
        // with a joker one pair is a three of a kind
        assertThat(new Hand("2245J", true).handRank()).isEqualTo(THREE_OF_A_KIND);
        // with 2 jokers a pair is a three of a kind
        assertThat(new Hand("6JQ9J", true).handRank()).isEqualTo(THREE_OF_A_KIND);
        // with a joker two pairs is a full house
        assertThat(new Hand("KKJ77", true).handRank()).isEqualTo(FULL_HOUSE);
        // with 2 jokers two pairs are a four of a kind
        assertThat(new Hand("KTJJT", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
        // with a joker three of a kind is a four of a kind
        assertThat(new Hand("KKKJ2", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
        // with 3 jokers three of a kind is a four of a kind
        assertThat(new Hand("JJJ23", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
        // with a joker four of a kind is a five of a kind
        assertThat(new Hand("KKKKJ", true).handRank()).isEqualTo(FIVE_OF_A_KIND);
        // with 4 jokers four of a kind is a five of a kind
        assertThat(new Hand("JJJJ2", true).handRank()).isEqualTo(FIVE_OF_A_KIND);
        // with 2 jokers a full house is a five of a kind
        assertThat(new Hand("KKKJJ", true).handRank()).isEqualTo(FIVE_OF_A_KIND);
        // with 3 jokers a full house is a five of a kind
        assertThat(new Hand("KKJJJ", true).handRank()).isEqualTo(FIVE_OF_A_KIND);
    }

    @Test
    void hand_rank_2_examples() {
        assertThat(new Hand("32T3K", true).handRank()).isEqualTo(ONE_PAIR);
        assertThat(new Hand("KK677", true).handRank()).isEqualTo(TWO_PAIRS);
        assertThat(new Hand("T55J5", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(new Hand("KTJJT", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(new Hand("QQQJA", true).handRank()).isEqualTo(FOUR_OF_A_KIND);
    }

    @Test
    void compare_two_pairs_2() {
        assertThat(new Hand("KK677", true)).isLessThan(new Hand("KTJJT", true));
    }

    @Test
    void part_1() {
        assertThat(Day07.part1(test)).isEqualTo(6440);
    }

    @Test
    void part_2() {
        assertThat(Day07.part2(test)).isEqualTo(5905);
    }
}
