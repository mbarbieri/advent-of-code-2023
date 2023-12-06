package xyz.barbieri.adventofcode;

import java.util.ArrayList;
import java.util.List;

public class Day06 {

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static String part1() {
        List<Race> input = List.of(
                new Race(49, 263),
                new Race(97, 1532),
                new Race(94, 1378),
                new Race(94, 1851));
        return input.stream().map(Race::winningCount).reduce((x, y) -> x * y).map(String::valueOf).orElseThrow();
    }

    private static String part2() {
        Race race = new Race(49979494L, 263153213781851L);
        return race.winningCount().toString();
    }

    public record Race(long time, long distance) {

        public List<Long> winning() {
            List<Long> result = new ArrayList<>();
            for (long i = 1; i < time; i++) {
                if (((time - i) * i) > distance) {
                    result.add(i);
                }
            }
            return result;
        }

        public Integer winningCount() {
            int count = 0;
            for (long i = 1; i < time; i++) {
                if (((time - i) * i) > distance) {
                    count++;
                }
            }
            return count;
        }
    }
}
