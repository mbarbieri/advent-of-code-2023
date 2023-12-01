package xyz.barbieri.adventofcode;

import org.apache.commons.lang3.CharUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day01 {

    private static Map<String, String> numbers = Map.of("one", "o1e", "two", "t2o", "three", "t3e", "four", "f4r", "five", "f5e", "six", "s6x", "seven", "s7n", "eight", "e8t", "nine", "n9e");

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    public static String part1() {
        return Utils.readLines("day01.txt").stream()
                .map(Day01::firstAndLastFromString)
                .reduce(Integer::sum)
                .orElseThrow().toString();
    }

    public static String part2() {
        return Utils.readLines("day01.txt").stream()
                .map(Day01::replaceSpelledNumbers)
                .map(Day01::firstAndLastFromString)
                .reduce(Integer::sum)
                .orElseThrow().toString();
    }

    public static Integer firstAndLastFromString(String line) {
        List<Character> numbers = new ArrayList<>();
        for (char c : line.toCharArray()) {
            if (CharUtils.isAsciiNumeric(c)) {
                numbers.add(c);
            }
        }
        char first = numbers.get(0);
        char last = numbers.get(numbers.size() - 1);
        return Integer.parseInt(first + String.valueOf(last));
    }

    public static String replaceSpelledNumbers(String line) {
        for (Map.Entry<String, String> entry : numbers.entrySet()) {
            line = line.replaceAll(entry.getKey(), entry.getValue());
        }
        return line;
    }
}
