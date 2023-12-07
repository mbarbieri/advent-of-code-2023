package xyz.barbieri.adventofcode;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day07 {
    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }
    private static Long part1() {
        return part1(Utils.readFile("day07.txt"));
    }

    private static Long part2() {
        return part2(Utils.readFile("day07.txt"));
    }

    public static Long part1(String input) {
        return computeResult(input, Hand::of);
    }

    public static Long part2(String input) {
        return computeResult(input, Hand::ofNewRules);
    }

    private static long computeResult(String input, Function<String, Hand> handBuilder) {
        List<Hand> orderedHands = Arrays.stream(input.split("\n"))
                .map(handBuilder)
                .sorted().toList();
        int rank = 1;
        long result = 0;
        for (Hand hand : orderedHands) {
            result += hand.bid * rank;
            rank++;
        }
        return result;
    }

    public record Hand(String hand, Integer bid, boolean newRules) implements Comparable<Hand> {

        public static final int HIGH_CARD = 1;
        public static final int ONE_PAIR = 2;
        public static final int TWO_PAIRS = 3;
        public static final int THREE_OF_A_KIND = 4;
        public static final int FULL_HOUSE = 5;
        public static final int FOUR_OF_A_KIND = 6;
        public static final int FIVE_OF_A_KIND = 7;

        public Hand(String hand) {
            this(hand, 1, false);
        }

        public Hand(String hand, boolean newRules) {
            this(hand, 1, newRules);
        }

        public Hand(String hand, Integer bid) {
            this(hand, bid, false);
        }

        public static Hand of(String input) {
            String[] split = input.split(" ");
            return new Hand(split[0], Integer.parseInt(split[1]), false);
        }

        public static Hand ofNewRules(String input) {
            String[] split = input.split(" ");
            return new Hand(split[0], Integer.parseInt(split[1]), true);
        }

        public Integer handRank() {
            Map<Character, Long> cardsCount = hand.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            int jokers = StringUtils.countMatches(hand, 'J');
            if (cardsCount.size() == 5) {
                return highCard(jokers);
            } else if (cardsCount.size() == 4) {
                return onePair(jokers);
            } else if (cardsCount.size() == 3) {
                if (cardsCount.values().stream().anyMatch(v -> v == 3)) {
                    return threeOfAKind(jokers);
                } else {
                    return twoPairs(jokers);
                }
            } else if (cardsCount.size() == 2) {
                if (cardsCount.values().stream().anyMatch(v -> v == 4)) {
                    return fourOfAKind(jokers);
                } else {
                    return fullHouse(jokers);
                }
            } else if (cardsCount.size() == 1) {
                return FIVE_OF_A_KIND;
            } else {
                throw new IllegalStateException("Invalid hand: " + hand);
            }
        }

        private int fullHouse(int jokers) {
            if (newRules && jokers >= 2) {
                return FIVE_OF_A_KIND;
            } else {
                return FULL_HOUSE;
            }
        }

        private int fourOfAKind(int jokers) {
            if (newRules && jokers >= 1) {
                return FIVE_OF_A_KIND;
            } else {
                return FOUR_OF_A_KIND;
            }
        }

        private int twoPairs(int jokers) {
            if (newRules && jokers == 1) {
                return FULL_HOUSE;
            } else if (newRules && jokers == 2) {
                return FOUR_OF_A_KIND;
            } else {
                return TWO_PAIRS;
            }
        }

        private int threeOfAKind(int jokers) {
            if (newRules && jokers >= 1) {
                return FOUR_OF_A_KIND;
            } else {
                return THREE_OF_A_KIND;
            }
        }

        private int onePair(int jokers) {
            if (newRules && jokers >= 1) {
                return THREE_OF_A_KIND;
            } else {
                return ONE_PAIR;
            }
        }

        private int highCard(int jokers) {
            if (newRules && jokers == 1) {
                return ONE_PAIR;
            } else {
                return HIGH_CARD;
            }
        }

        @Override
        public int compareTo(Hand other) {
            Integer thisRank = this.handRank();
            Integer otherRank = other.handRank();
            if (thisRank.equals(otherRank)) {
                return this.compareHighCard(other);
            } else {
                return thisRank.compareTo(otherRank);
            }
        }

        private int compareHighCard(Hand other) {
            for (int i = 0; i < hand.length(); i++) {
                if (this.cardRankAt(i) != other.cardRankAt(i)) {
                    return Integer.compare(this.cardRankAt(i), other.cardRankAt(i));
                }
            }
            return 0;
        }

        private int cardRankAt(int i) {
            return switch (hand.charAt(i)) {
                case 'T' -> 10;
                case 'J' -> newRules ? 1 : 11;
                case 'Q' -> 12;
                case 'K' -> 13;
                case 'A' -> 14;
                default -> Integer.parseInt(String.valueOf(hand.charAt(i)));
            };
        }
    }
}
