package xyz.barbieri.adventofcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 {

    public static History parseHistory(String input) {
        return new History(input);
    }

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static String part1() {
        return Utils.readLines("day09.txt")
                .parallelStream()
                .map(Day09::parseHistory)
                .map(History::nextValue)
                .reduce(0, Integer::sum).toString();
    }

    private static String part2() {
        return Utils.readLines("day09.txt")
                .parallelStream()
                .map(Day09::parseHistory)
                .map(History::previousValue)
                .reduce(0, Integer::sum).toString();
    }

    public static class History {

        private final Sequence firstSequence;

        public History(String input) {
            firstSequence = new Sequence(List.of(input.split(" ")).stream().map(Integer::parseInt).toList());
        }

        public List<Integer> values() {
            return firstSequence.values();
        }

        public Integer nextValue() {
            Sequence currentSequence = firstSequence;
            List<Integer> lastValues = new ArrayList<>();
            while (!currentSequence.isZero()) {
                lastValues.add(currentSequence.last());
                currentSequence = currentSequence.next();
            }
            return lastValues.stream().reduce(0, Integer::sum);
        }

        public Integer previousValue() {
            Sequence currentSequence = firstSequence;
            List<Integer> firstValues = new ArrayList<>();
            while (!currentSequence.isZero()) {
                firstValues.add(currentSequence.first());
                currentSequence = currentSequence.next();
            }
            Collections.reverse(firstValues);
            return firstValues.stream().reduce(0, (a, b) -> b - a);
        }
    }

    public record Sequence(List<Integer> values) {

        public Sequence next() {
            if (values.size() == 1) {
                return new Sequence(List.of(values.get(0)));
            } else {
                List<Integer> nextValues = new ArrayList<>();
                for (int i = 1; i < values.size(); i++) {
                    nextValues.add(values.get(i) - values.get(i - 1));
                }
                return new Sequence(nextValues);
            }
        }

        public boolean isZero() {
            return values.stream().allMatch(v -> v == 0);
        }

        public Integer last() {
            return values().get(values().size() - 1);
        }

        public Integer first() {
            return values().get(0);
        }
    }
}
