package com.ikueb.advent18

import com.ikueb.advent18.Day13.getFirstCollision
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day13Test {

    @Test
    fun partOneExample() {
        assertThat(getFirstCollision(example)).isEqualTo(Point(7, 3))
    }

    @Test
    fun partOne() {
        assertThat(getFirstCollision(getInput("Day13"))).isEqualTo(Point(117, 62))
    }

    private val example = listOf(
            "/->-\\        ",
            "|   |  /----\\",
            "| /-+--+-\\  |",
            "| | |  | v  |",
            "\\-+-/  \\-+--/",
            "  \\------/   "
    )
}