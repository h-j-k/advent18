package com.ikueb.advent18

import com.ikueb.advent18.Day15.getBattleOutcome
import com.ikueb.advent18.Day15.getModifiedBattleOutcome
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day15Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "27730 | 7 | ########.G...##...EG##.#.#G##..G#E##.....########",
        "36334 | 7 | ########G..#E##E#E.E##G.##.##...#E##...E.########",
        "39514 | 7 | ########E..EG##.#G.E##E.##E##G..#.##..E#.########",
        "27755 | 7 | ########E.G#.##.#G..##G.#.G##G..#.##...E.########",
        "28944 | 7 | ########.E...##.#..G##.###.##E#G#G##...#G########",
        "18740 | 9 | ##########G......##.E.#...##..##..G##...##..##...#...##.G...G.##.....G.##########"
    ])
    fun partOneExample(expected: Int, column: Int, input: String) {
        val grid = (0 until input.length / column)
                .map { i -> input.substring(i * column, i * column + column) }
        assertThat(getBattleOutcome(grid)).isEqualTo(expected)
    }

    @Test
    fun partOne() {
        assertThat(getBattleOutcome(getInput("Day15"))).isEqualTo(261855)
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "31284 | 7 | ########E..EG##.#G.E##E.##E##G..#.##..E#.########",
        "3478 | 7 | ########E.G#.##.#G..##G.#.G##G..#.##...E.########",
        "6474 | 7 | ########.E...##.#..G##.###.##E#G#G##...#G########"
    ])
    fun partTwoExample(expected: Int, column: Int, input: String) {
        val grid = (0 until input.length / column)
                .map { i -> input.substring(i * column, i * column + column) }
        assertThat(getModifiedBattleOutcome(grid)).isEqualTo(expected)
    }

    @Test
    fun partTwo() {
        assertThat(getModifiedBattleOutcome(getInput("Day15"))).isEqualTo(59568)
    }
}