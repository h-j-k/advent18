package com.ikueb.advent18

import com.ikueb.advent18.Day20.getPassMostDoors
import com.ikueb.advent18.Day20.getPassThrough
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day20Test {

    @Test
    fun partOneExample() {
        assertThat(getPassMostDoors(example)).isEqualTo(31)
    }

    @Test
    fun partOne() {
        assertThat(getPassMostDoors(getSingleInput("Day20"))).isEqualTo(4108)
    }

    @Test
    fun partTwo() {
        assertThat(getPassThrough(getSingleInput("Day20"))).isEqualTo(8366)
    }

    private val example = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"
}