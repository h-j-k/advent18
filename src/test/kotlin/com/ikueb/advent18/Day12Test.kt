package com.ikueb.advent18

import com.ikueb.advent18.Day12.getSumOfPlantedPots
import com.ikueb.advent18.Day12.getSumOfPlantedPotsAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day12Test {

    @Test
    fun partOneExample() {
        assertThat(getSumOfPlantedPots(example)).isEqualTo(325)
    }

    @Test
    fun partOne() {
        assertThat(getSumOfPlantedPots(getInput("Day12"))).isEqualTo(6201)
    }

    @Test
    fun partTwo() {
        assertThat(getSumOfPlantedPotsAt(getInput("Day12"), 50000000000))
                .isEqualTo(9300000001023)
    }

    private val example = listOf(
            "initial state: #..#.#..##......###...###",
            "",
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
    )
}