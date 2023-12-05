package xyz.barbieri.adventofcode;

import org.apache.commons.collections4.SetUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static String part1() { // 32001
        return Utils.readLines("day04.txt")
                .stream()
                .map(Day04::extractedNumbers)
                .map(Day04::points)
                .reduce(Integer::sum)
                .map(String::valueOf)
                .orElseThrow();
    }

    private static String part2() { // 5037841
        List<String> lines = Utils.readLines("day04.txt");
        return winningCardsCount(lines).toString();
    }

    public static Set<Integer> winningNumbers(String card) {
        return Arrays.stream(card
                        .split(": ")[1]
                        .split("\\|")[0]
                        .trim()
                        .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public static Set<Integer> cardNumbers(String card) {
        return Arrays.stream(card
                        .split(": ")[1]
                        .split("\\|")[1]
                        .trim()
                        .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public static Set<Integer> extractedNumbers(String card) {
        Set<Integer> winningNumbers = winningNumbers(card);
        Set<Integer> cardNumbers = cardNumbers(card);
        return SetUtils.intersection(winningNumbers, cardNumbers);
    }

    public static Integer points(Set<Integer> numbers) {
        return (int) Math.pow(2, numbers.size() - 1);
    }

    public static Integer winningCardsCount(List<String> lines) {
        Map<Integer, Integer> cardsCount = new HashMap<>();
        IntStream.range(0, lines.size()).forEach(i -> cardsCount.put(i, 1));

        for (int i = 0; i < lines.size(); i++) {
            var card = lines.get(i);
            var winningCards = extractedNumbers(card).size();
            for (int w = 1; w <= winningCards; w++) {
                cardsCount.put(i + w, cardsCount.get(i + w) + cardsCount.get(i));
            }
        }
        return cardsCount.values().stream().reduce(Integer::sum).orElseThrow();
    }
}
