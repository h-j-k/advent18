package com.ikueb.advent18

import com.ikueb.advent18.Day06.getLargestDefiniteArea
import com.ikueb.advent18.Day06.getLargestRegionWithManhattanDistanceSumLessThan
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun partOneExample() {
        assertThat(getLargestDefiniteArea(example)).isEqualTo(17)
    }

    @Test
    fun partOne() {
        assertThat(getLargestDefiniteArea(getInput("Day06"))).isEqualTo(2342)
    }

    @Test
    fun partTwoExample() {
        assertThat(getLargestRegionWithManhattanDistanceSumLessThan(example, 32))
                .isEqualTo(16)
    }

    @Test
    fun partTwo() {
        assertThat(getLargestRegionWithManhattanDistanceSumLessThan(getInput("Day06"), 10000))
                .isEqualTo(43302)
    }

    private val example = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
    )
}