package com.ikueb.advent18

import com.ikueb.advent18.Day18.getResourceValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day18Test {

    @Test
    fun partOneExample() {
        assertThat(getResourceValue(example)).isEqualTo(1147)
    }

    @Test
    fun partOne() {
        assertThat(getResourceValue(getInput("Day18"))).isEqualTo(737800)
    }

    private val example = listOf(
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."
    )
}