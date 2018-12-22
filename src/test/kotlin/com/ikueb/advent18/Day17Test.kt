package com.ikueb.advent18

import com.ikueb.advent18.Day17.getWaterReach
import com.ikueb.advent18.Day17.getWaterRetained
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day17Test {

    @Test
    fun partOneExample() {
        assertThat(getWaterReach(example)).isEqualTo(57)
    }

    @Test
    fun partOne() {
        assertThat(getWaterReach(getInput("Day17"))).isEqualTo(38409)
    }

    @Test
    fun partTwoExample() {
        assertThat(getWaterRetained(example)).isEqualTo(29)
    }

    @Test
    fun partTwo() {
        assertThat(getWaterRetained(getInput("Day17"))).isEqualTo(32288)
    }

    private val example = listOf(
            "x=495, y=2..7",
            "y=7, x=495..501",
            "x=501, y=3..7",
            "x=498, y=2..4",
            "x=506, y=1..2",
            "x=498, y=10..13",
            "x=504, y=10..13",
            "y=13, x=498..504"
    )
}