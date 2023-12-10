package xyz.barbieri.adventofcode;

import org.junit.jupiter.api.Test;
import xyz.barbieri.adventofcode.Day10.PipesArea;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    String test = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....""".stripIndent();

    String test1 = """
            -L|F7
            7S-7|
            L|7||
            -L-J|
            L|-JF""".stripIndent();

    String loopTest1 = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........""".stripIndent();

    String loopTest2 = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...""".stripIndent();

    String loopTest3 = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L""".stripIndent();

    @Test
    void max_distance() {
        PipesArea pipesArea = new PipesArea(test);
        assertThat(pipesArea.navigateLoop()).isEqualTo(4);
    }

    @Test
    void max_distance_1() {
        PipesArea pipesArea = new PipesArea(test1);
        assertThat(pipesArea.navigateLoop()).isEqualTo(4);
    }

    @Test
    void solution_part_1() {
        assertThat(Day10.part1()).isEqualTo("6927");
    }

    @Test
    void count_points_in_loop() {
        PipesArea pipesArea = new PipesArea(loopTest1);
        assertThat(pipesArea.countPointsInLoop()).isEqualTo(4);
    }

    @Test
    void count_points_in_loop_2() {
        PipesArea pipesArea = new PipesArea(loopTest2);
        assertThat(pipesArea.countPointsInLoop()).isEqualTo(8);
    }

    @Test
    void count_points_in_loop_3() {
        PipesArea pipesArea = new PipesArea(loopTest3);
        assertThat(pipesArea.countPointsInLoop()).isEqualTo(10);
    }

    @Test
    void solution_part_2() {
        assertThat(Day10.part2()).isEqualTo("467");
    }
}
