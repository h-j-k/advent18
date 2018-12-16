package com.ikueb.advent18

import com.ikueb.advent18.Day13.getFirstCollision
import com.ikueb.advent18.Day13.getLastCartStanding
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day13Test {

    @Test
    fun partOneExample() {
        assertThat(getFirstCollision(partOneExample)).isEqualTo(Point(7, 3))
    }

    @Test
    fun partOne() {
        assertThat(getFirstCollision(getInput("Day13"))).isEqualTo(Point(117, 62))
    }

    @Test
    fun partTwoExample() {
        assertThat(getLastCartStanding(partTwoExample)).isEqualTo(Point(6, 4))
    }

    @Test
    fun partTwo() {
        assertThat(getLastCartStanding(getInput("Day13"))).isEqualTo(Point(69, 67))
    }

    private val partOneExample = listOf(
            "/->-\\        ",
            "|   |  /----\\",
            "| /-+--+-\\  |",
            "| | |  | v  |",
            "\\-+-/  \\-+--/",
            "  \\------/   "
    )

    private val partTwoExample = listOf(
            "/>-<\\  ",
            "|   |  ",
            "| /<+-\\",
            "| | | v",
            "\\>+</ |",
            "  |   ^",
            "  \\<->/\n"
    )
}