package xyz.barbieri.adventofcode;

import org.apache.commons.lang3.CharUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    private static final Pattern numPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static String part1() { // 560670
        String file = Utils.readFile("day03.txt");
        Set<Coord> symbolsCoordinates = symbolsCoordinates(file);
        List<Integer> numbers = findValidNumbers(file, symbolsCoordinates);
        return numbers.stream().reduce(Integer::sum).orElseThrow().toString();
    }

    private static String part2() { // 91622824
        String file = Utils.readFile("day03.txt");
        return gearRatios(file).toString();
    }


    public static Set<Coord> symbolsCoordinates(String file) {
        Set<Coord> result = new HashSet<>();
        String[] rows = file.split("\n");
        for (int rowIdx = 0; rowIdx < rows.length; rowIdx++) {
            String row = rows[rowIdx];
            for (int columnIdx = 0; columnIdx < row.length(); columnIdx++) {
                if (!CharUtils.isAsciiNumeric(row.charAt(columnIdx)) && row.charAt(columnIdx) != '.') {
                    result.add(new Coord(columnIdx, rowIdx));
                }
            }
        }
        return result;
    }


    public static List<Integer> findValidNumbers(String file, Set<Coord> symbolsCoordinates) {
        List<Integer> result = new ArrayList<>();
        String[] rows = file.split("\n");
        for (int rowIdx = 0; rowIdx < rows.length; rowIdx++) {
            String row = rows[rowIdx];
            Matcher matcher = numPattern.matcher(row);
            while (matcher.find()) {
                int numFound = Integer.parseInt(matcher.group());
                if (isValid(rowIdx, matcher.start(), matcher.end(), symbolsCoordinates)) {
                    result.add(numFound);
                }
            }
        }
        return result;
    }

    public static boolean isValid(int rowIdx, int colStart, int colEnd, Set<Coord> symbolsCoordinates) {
        for (int colIdx = colStart - 1; colIdx <= colEnd; colIdx++) {
            if (symbolsCoordinates.contains(new Coord(colIdx, rowIdx))) {
                return true;
            }
            if (symbolsCoordinates.contains(new Coord(colIdx, rowIdx - 1))) {
                return true;
            }
            if (symbolsCoordinates.contains(new Coord(colIdx, rowIdx + 1))) {
                return true;
            }
        }
        return false;
    }

    public static Integer gearRatios(String file) {
        Integer total = 0;
        String[] rows = file.split("\n");
        for (int rowIdx = 0; rowIdx < rows.length; rowIdx++) {
            String row = rows[rowIdx];
            for (int columnIdx = 0; columnIdx < row.length(); columnIdx++) {
                if (row.charAt(columnIdx) == '*') {
                    var numbers = findValidNumbers(file, Set.of(new Coord(columnIdx, rowIdx)));
                    if (numbers.size() == 2) {
                        Integer gearRatio = numbers.get(0) * numbers.get(1);
                        total += gearRatio;
                    }
                }
            }
        }
        return total;
    }
    public record Coord(int column, int row) {}
}
