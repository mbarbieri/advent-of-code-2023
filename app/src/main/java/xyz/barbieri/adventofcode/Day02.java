package xyz.barbieri.adventofcode;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    public static String part1() {
        return Utils.readLines("day02.txt").stream().map(Day02::parse).filter(Game::isPossible).map(Game::id).reduce(Integer::sum).orElseThrow().toString();
    }

    public static String part2() {
        return "";
    }

    public static Game parse(String line) {
        String[] idAndRounds = StringUtils.split(line, ":");
        int id = Integer.parseInt(idAndRounds[0].replaceAll("Game ", "").trim());
        String[] rounds = StringUtils.split(idAndRounds[1], ";");
        List<Round> resultRounds = new ArrayList<>();
        for (String round : rounds) {
            int red = 0;
            int green = 0;
            int blue = 0;
            String[] colors = StringUtils.split(round, ",");
            for (String color : colors) {
                String[] colorAndNumber = StringUtils.split(color, " ");
                String colorName = colorAndNumber[1].trim();
                int number = Integer.parseInt(colorAndNumber[0].trim());
                switch (colorName) {
                    case "red" -> red = number;
                    case "green" -> green = number;
                    case "blue" -> blue = number;
                }
            }
            resultRounds.add(new Round(red, green, blue));
        }
        return new Game(id, resultRounds);
    }

    public record Game(int id, List<Round> rounds) {

        boolean isPossible() {
            return rounds.stream().allMatch(Round::isPossible);
        }
    }

    public record Round(int red, int green, int blue) {

        boolean isPossible() {
            return red <= 12 && green <= 13 && blue <= 14;
        }
    }

}
