package com.ikueb.advent18

import com.ikueb.advent18.Day20.getPassMostDoors
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

    private val example = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"
}