package xyz.barbieri.adventofcode;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 {

    public static void main(String[] args) {
        String input = Utils.readFile("day08.txt");
        System.out.println("Part 1: " + countSteps(parseInput(input)));
        System.out.println("Part 2: " + countStepsPart2Parallel(parseInput(input)));
    }

    public static Navigation parseInput(String input) {
        String[] split = input.split("\n");
        List<Character> directions = split[0].chars().mapToObj(c -> (char) c).toList();
        Map<String, Pair<String, String>> nodes = new HashMap<>();

        Arrays.stream(split).skip(2).forEach(s -> {
            String start = s.split(" = ")[0];
            String left = s.split(" = ")[1].split(", ")[0].substring(1);
            String right = s.split(" = ")[1].split(", ")[1].substring(0, 3);
            nodes.put(start, Pair.of(left, right));
        });
        return new Navigation(directions, nodes);
    }

    public static Integer countSteps(Navigation navigation) {
        int steps = 0;
        String current = "AAA";
        int i = 0;
        while (!current.equals("ZZZ")) {
            Character dir = navigation.directions.get(i);
            if (dir.equals('R')) {
                current = navigation.nodes().get(current).getRight();
            } else {
                current = navigation.nodes().get(current).getLeft();
            }
            if (i < navigation.directions().size() - 1) {
                i++;
            } else {
                i = 0;
            }
            steps++;
        }
        return steps;
    }

    public static Integer countStepsPart2(Navigation navigation) {
        int steps = 0;
        List<String> currentNodes = navigation.startingNodes();
        int i = 0;
        while (!areAllEndingNodes(currentNodes)) {
            Character dir = navigation.directions.get(i);
            currentNodes = nextNodes(navigation, currentNodes, dir);
            if (i < navigation.directions().size() - 1) {
                i++;
            } else {
                i = 0;
            }
            steps++;
            System.out.println(steps);
        }
        return steps;
    }

    public static BigInteger countStepsPart2Parallel(Navigation navigation) {
        List<String> currentNodes = navigation.startingNodes();
        return currentNodes.stream().parallel().map(n -> countStepsPart2FromNode(navigation, n)).map(BigInteger::valueOf).reduce(BigInteger.ONE, Day08::lcm);
    }

    public static Integer countStepsPart2FromNode(Navigation navigation, String node) {
        int steps = 0;
        String current = node;
        int i = 0;
        while (!current.endsWith("Z")) {
            Character dir = navigation.directions.get(i);
            if (dir.equals('R')) {
                current = navigation.nodes().get(current).getRight();
            } else {
                current = navigation.nodes().get(current).getLeft();
            }
            if (i < navigation.directions().size() - 1) {
                i++;
            } else {
                i = 0;
            }
            steps++;
        }
        return steps;
    }

    private static BigInteger lcm(BigInteger first, BigInteger second) {
        BigInteger gcd = first.gcd(second);
        BigInteger absProduct = first.multiply(second).abs();
        return absProduct.divide(gcd);
    }

    private static List<String> nextNodes(Navigation navigation, List<String> currentNodes, Character dir) {
        List<String> nextNodes = new ArrayList<>();
        for (String currentNode : currentNodes) {
            if (dir.equals('R')) {
                nextNodes.add(navigation.nodes.get(currentNode).getRight());
            } else {
                nextNodes.add(navigation.nodes.get(currentNode).getLeft());
            }
        }
        return nextNodes;
    }

    private static boolean areAllEndingNodes(List<String> nodes) {
        return nodes.stream().allMatch(n -> n.endsWith("Z"));
    }

    public static class Navigation {

        private final List<Character> directions;
        private final Map<String, Pair<String, String>> nodes;

        public Navigation(List<Character> directions, Map<String, Pair<String, String>> nodes) {
            this.directions = directions;
            this.nodes = nodes;
        }

        public List<Character> directions() {
            return directions;
        }

        public Map<String, Pair<String, String>> nodes() {
            return nodes;
        }

        public List<String> startingNodes() {
            return nodes.keySet().stream().filter(l -> l.endsWith("A")).toList();
        }
    }
}
