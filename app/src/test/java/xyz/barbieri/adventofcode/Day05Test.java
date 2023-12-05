package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day05.AlmanacMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

    private String test = """
            seeds: 79 14 55 13
                        
            seed-to-soil map:
            50 98 2
            52 50 48
                        
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
                        
            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4
                        
            water-to-light map:
            88 18 7
            18 25 70
                        
            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13
                        
            temperature-to-humidity map:
            0 69 1
            1 0 69
                        
            humidity-to-location map:
            60 56 37
            56 93 4
            """.stripIndent();

    @Test
    void parse_seeds() {
        assertThat(Day05.parseSeeds(test)).isEqualTo(List.of(79L, 14L, 55L, 13L));
    }

    @Test
    void parse_map() {
        AlmanacMap map = Day05.parseMap(test, "seed-to-soil");
        assertThat(map).isNotNull();
        assertThat(map.getDestination(0)).isEqualTo(0);
        assertThat(map.getDestination(1)).isEqualTo(1);

        assertThat(map.getDestination(48)).isEqualTo(48);
        assertThat(map.getDestination(49)).isEqualTo(49);
        assertThat(map.getDestination(50)).isEqualTo(52);
        assertThat(map.getDestination(51)).isEqualTo(53);

        assertThat(map.getDestination(96)).isEqualTo(98);
        assertThat(map.getDestination(97)).isEqualTo(99);
        assertThat(map.getDestination(98)).isEqualTo(50);
        assertThat(map.getDestination(99)).isEqualTo(51);
    }

    @Test
    void parse_another_map() {
        AlmanacMap map = Day05.parseMap(test, "soil-to-fertilizer");
        assertThat(map).isNotNull();
        assertThat(map.getDestination(0)).isEqualTo(39);
        assertThat(map.getDestination(1)).isEqualTo(40);

        assertThat(map.getDestination(15)).isEqualTo(0);
        assertThat(map.getDestination(16)).isEqualTo(1);
    }

    @Test
    void minimum_destination() {
        List<AlmanacMap> maps = List.of(
                Day05.parseMap(test, "seed-to-soil"),
                Day05.parseMap(test, "soil-to-fertilizer"),
                Day05.parseMap(test, "fertilizer-to-water"),
                Day05.parseMap(test, "water-to-light"),
                Day05.parseMap(test, "light-to-temperature"),
                Day05.parseMap(test, "temperature-to-humidity"),
                Day05.parseMap(test, "humidity-to-location"));
        assertThat(Day05.minDestination(Day05.parseSeeds(test).iterator(), maps)).isEqualTo(35L);
    }
}
