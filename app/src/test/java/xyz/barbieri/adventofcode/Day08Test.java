package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day08.Navigation;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.barbieri.adventofcode.Day08.countStepsPart2Parallel;
import static xyz.barbieri.adventofcode.Day08.parseInput;

class Day08Test {
    String test1 = """
            RL
                        
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)""".stripIndent();

    String test2 = """
            LLR
                       
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)""".stripIndent();

    String test3 = """
            LR
                        
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)""".stripIndent();

    @Test
    void parse_input() {
        Navigation n = parseInput(test1);
        assertThat(n.directions()).containsExactly('R', 'L');
        assertThat(n.nodes().size()).isEqualTo(7);
        assertThat(n.nodes().get("AAA").getLeft()).isEqualTo("BBB");
        assertThat(n.nodes().get("AAA").getRight()).isEqualTo("CCC");
    }

    @Test
    void count_steps() {
        Navigation n = parseInput(test1);
        assertThat(Day08.countStepsPart1(n)).isEqualTo(2);
    }

    @Test
    void count_steps_2() {
        Navigation n = parseInput(test2);
        assertThat(Day08.countStepsPart1(n)).isEqualTo(6);
    }

    @Test
    void part2_starting_nodes() {
        Navigation n = parseInput(test3);
        assertThat(n.startingNodes()).containsExactly("11A", "22A");
    }

    @Test
    void count_steps_part2() {
        Navigation n = parseInput(test3);
        assertThat(Day08.countStepsPart2Slow(n)).isEqualTo(6);
    }

    @Test
    void count_steps_part2_parallel() {
        Navigation n = parseInput(test3);
        assertThat(countStepsPart2Parallel(n)).isEqualTo(6);
    }

    @Test
    void solve_part2() {
        BigInteger solution = countStepsPart2Parallel(parseInput(Utils.readFile("day08.txt")));
        assertThat(solution).isEqualTo(BigInteger.valueOf(11678319315857L));
    }
}
