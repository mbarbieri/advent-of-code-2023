package xyz.barbieri.adventofcode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    public static String part1() {
        String input = Utils.readFile("day10.txt");
        PipesArea pipesArea = new PipesArea(input);
        return String.valueOf(pipesArea.navigateLoop());
    }

    public static String part2() {
        String input = Utils.readFile("day10.txt");
        PipesArea pipesArea = new PipesArea(input);
        return String.valueOf(pipesArea.countPointsInLoop());
    }

    public static class PipesArea {

        private final Character[][] map;
        private final Set<Node> loopNodes;

        public PipesArea(String input) {
            this.map = initMap(input);
            this.loopNodes = new HashSet<>();
        }

        public int navigateLoop() {
            Node start = findStart();
            loopNodes.add(start);

            int distance = 0;
            Collection<Node> neighbours = findNeighbours(start);
            while (!neighbours.isEmpty()) {
                distance++;
                Set<Node> newNeighbours = new HashSet<>();
                for (Node neighbour : neighbours) {
                    if (!loopNodes.contains(neighbour)) {
                        loopNodes.add(neighbour);
                        newNeighbours.addAll(findNeighbours(neighbour));
                    }
                }
                neighbours = newNeighbours;
            }
            return distance;
        }

        private Collection<Node> findNeighbours(Node currentNode) {
            Set<Node> neighbours = new HashSet<>();
            Character currentPipe = map[currentNode.row][currentNode.column];
            if (currentPipe == '.') {
                return neighbours;
            } else {
                if (currentPipe == '-') {
                    addLeft(currentNode, neighbours);
                    addRight(currentNode, neighbours);
                } else if (currentPipe == '7') {
                    addDown(currentNode, neighbours);
                    addLeft(currentNode, neighbours);
                } else if (currentPipe == 'J') {
                    addUp(currentNode, neighbours);
                    addLeft(currentNode, neighbours);
                } else if (currentPipe == 'L') {
                    addUp(currentNode, neighbours);
                    addRight(currentNode, neighbours);
                } else if (currentPipe == '|') {
                    addUp(currentNode, neighbours);
                    addDown(currentNode, neighbours);
                } else if (currentPipe == 'F') {
                    addDown(currentNode, neighbours);
                    addRight(currentNode, neighbours);
                } else if (currentPipe == 'S') {
                    addUp(currentNode, neighbours);
                    addDown(currentNode, neighbours);
                    addRight(currentNode, neighbours);
                    addLeft(currentNode, neighbours);
                } else {
                    throw new IllegalArgumentException("Unknown pipe: " + currentPipe);
                }
                return neighbours.stream().filter(n -> !loopNodes.contains(n)).collect(Collectors.toSet());
            }
        }

        private boolean canGoDown(Node n) {
            Character down = map[n.row + 1][n.column];
            return down == 'J' || down == 'L' || down == '|';
        }

        private void addDown(Node currentNode, Set<Node> neighbours) {
            if (canGoDown(currentNode)) {
                neighbours.add(currentNode.down());
            }
        }

        private boolean canGoUp(Node n) {
            if (n.row != 0) {
                Character up = map[n.row - 1][n.column];
                return up == '7' || up == 'F' || up == '|';
            } else {
                return false;
            }
        }

        private void addUp(Node currentNode, Set<Node> neighbours) {
            if (canGoUp(currentNode)) {
                neighbours.add(currentNode.up());
            }
        }

        private boolean canGoRight(Node n) {
            Character right = map[n.row][n.column + 1];
            return right == 'J' || right == '7' || right == '-';
        }

        private void addRight(Node currentNode, Set<Node> neighbours) {
            if (canGoRight(currentNode)) {
                neighbours.add(currentNode.right());
            }
        }

        private boolean canGoLeft(Node n) {
            Character left = map[n.row][n.column - 1];
            return left == 'L' || left == 'F' || left == '-';
        }

        private void addLeft(Node currentNode, Set<Node> neighbours) {
            if (canGoLeft(currentNode)) {
                neighbours.add(currentNode.left());
            }
        }

        public Node findStart() {
            for (int row = 0; row < map.length; row++) {
                int column = 0;
                for (char c : map[row]) {
                    if (c == 'S') {
                        return new Node(row, column);
                    }
                    column++;
                }
            }
            return null;
        }

        private Character[][] initMap(String input) {
            String[] rows = input.split("\n");
            Character[][] result = new Character[rows.length][rows[0].length()];
            int row = 0;
            for (String line : rows) {
                int column = 0;
                for (char c : line.toCharArray()) {
                    result[row][column] = c;
                    column++;
                }
                row++;
            }
            return result;
        }

        public int countPointsInLoop() {
            navigateLoop(); // only to populate loop nodes
            Node start = findStart();
            map[start.row][start.column] = findStartReplacement(start);
            int count = 0;
            for (int row = 0; row < map.length; row++) {
                Character[] charsInRow = map[row];

                for (int column = 0; column < charsInRow.length; column++) {
                    if (!loopNodes.contains(new Node(row, column)) && (isInsideLoop(new Node(row, column)))) {
                        count++;
                    }
                }
            }
            return count;
        }

        private Character findStartReplacement(Node start) {
            if (canGoUp(start) && canGoDown(start)) {
                return '|';
            } else if (canGoLeft(start) && canGoRight(start)) {
                return '-';
            } else if (canGoLeft(start) && canGoDown(start)) {
                return '7';
            } else if (canGoLeft(start) && canGoUp(start)) {
                return 'J';
            } else if (canGoUp(start) && canGoRight(start)) {
                return 'L';
            } else if (canGoDown(start) && canGoRight(start)) {
                return 'F';
            }
            throw new IllegalStateException("Start is not connected to any pipe");
        }

        private boolean isInsideLoop(Node node) {
            boolean isInLoop = false;
            char groupStart = ' ';
            for (int i = node.column + 1; i < map[0].length; i++) {
                if (loopNodes.contains(new Node(node.row, i))) {
                    Character currentNode = map[node.row][i];
                    // because only | F--J and L--7 are loop borders
                    if ((currentNode == '|') ||
                            (currentNode == 'J' && groupStart == 'F') ||
                            (currentNode == '7' && groupStart == 'L')) {
                        isInLoop = !isInLoop;
                        groupStart = ' ';
                    } else if (currentNode == 'F' || currentNode == 'L') {
                        groupStart = currentNode;
                    } else if ((currentNode == '7' && groupStart == 'F') ||
                            (currentNode == 'J' && groupStart == 'L')) {
                        groupStart = ' ';
                    }
                }
            }
            return isInLoop;
        }
    }

    public record Node(int row, int column) {

        public Node up() {
            return new Node(row - 1, column);
        }

        public Node down() {
            return new Node(row + 1, column);
        }

        public Node left() {
            return new Node(row, column - 1);
        }

        public Node right() {
            return new Node(row, column + 1);
        }
    }
}
