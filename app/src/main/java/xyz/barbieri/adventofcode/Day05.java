package xyz.barbieri.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Day05 {

    public static void main(String[] args) {
        String input = Utils.readFile("day05.txt");
        List<AlmanacMap> maps = List.of(
                parseMap(input, "seed-to-soil"),
                parseMap(input, "soil-to-fertilizer"),
                parseMap(input, "fertilizer-to-water"),
                parseMap(input, "water-to-light"),
                parseMap(input, "light-to-temperature"),
                parseMap(input, "temperature-to-humidity"),
                parseMap(input, "humidity-to-location"));
        System.out.println("Part 1: " + part1(input, maps));
        System.out.println("Part 2: " + part2(input, maps));
    }

    private static String part1(String input, List<AlmanacMap> maps) {
        List<Long> seeds = parseSeeds(input);
        return String.valueOf(minDestination(seeds.iterator(), maps));
    }

    private static String part2(String input, List<AlmanacMap> maps) {
        List<SeedIterator> seeds = parseSeedsIterators(input);
        List<Long> minLocations = seeds.stream().map(seedIterator -> minDestination(seedIterator, maps)).toList();
        return minLocations.stream().reduce(Long::min).map(String::valueOf).orElseThrow();
    }

    public static Long minDestination(Iterator<Long> seeds, List<AlmanacMap> maps) {
        long min = Long.MAX_VALUE;
        while (seeds.hasNext()) {
            Long source = seeds.next();
            for (AlmanacMap map : maps) {
                source = map.getDestination(source);
            }
            min = Math.min(min, source);
        }
        return min;
    }

    private static List<SeedIterator> parseSeedsIterators(String input) {
        List<SeedIterator> seeds = new ArrayList<>();
        String line = input.split("\n")[0].split(": ")[1];
        String[] seedsInput = line.split(" ");
        for (int i = 0; i < seedsInput.length; i = i + 2) {
            seeds.add(new SeedIterator(Long.parseLong(seedsInput[i]), Long.parseLong(seedsInput[i + 1])));
        }
        return seeds;
    }

    public static List<Long> parseSeeds(String input) {
        String firstLine = input.split("\n")[0];
        return Arrays.stream(firstLine.split(": ")[1]
                        .split(" "))
                .map(Long::parseLong).toList();
    }

    public static AlmanacMap parseMap(String input, String name) {
        String[] lines = input.split("\n");
        int lineIndex = Arrays.asList(lines).indexOf(name + " map:") + 1;
        AlmanacMap map = new AlmanacMap();
        while (lineIndex < lines.length && !lines[lineIndex].isEmpty()) {
            String[] mapInput = lines[lineIndex].split(" ");
            map.add(Long.parseLong(mapInput[0]), Long.parseLong(mapInput[1]), Long.parseLong(mapInput[2]));
            lineIndex++;
        }
        return map;
    }

    public static class AlmanacMap {

        private final List<Range> ranges = new ArrayList<>();

        public Long getDestination(long source) {
            for (Range range : ranges) {
                if (range.isInRange(source)) {
                    return range.getDestination(source);
                }
            }
            return source;
        }

        public void add(long destinationRageStart, long sourceRangeStart, long rangeLength) {
            ranges.add(new Range(sourceRangeStart, destinationRageStart, rangeLength));
        }
    }

    public static class Range {

        private final long sourceRangeStart;
        private final long destinationRageStart;
        private final long rangeLength;

        public Range(long sourceRangeStart, long destinationRageStart, long rangeLength) {
            this.sourceRangeStart = sourceRangeStart;
            this.destinationRageStart = destinationRageStart;
            this.rangeLength = rangeLength;
        }

        public boolean isInRange(long source) {
            return source >= sourceRangeStart && source < sourceRangeStart + rangeLength;
        }

        public Long getDestination(long source) {
            return destinationRageStart + (source - sourceRangeStart);
        }
    }

    private static class SeedIterator implements Iterator<Long> {

        private final long start;
        private final long length;
        private long current;

        public SeedIterator(long start, long length) {
            this.start = start;
            this.length = length;
            this.current = start;
        }

        @Override
        public boolean hasNext() {
            return current < start + length;
        }

        @Override
        public Long next() {
            return current++;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Long> action) {
            throw new UnsupportedOperationException();
        }
    }
}
