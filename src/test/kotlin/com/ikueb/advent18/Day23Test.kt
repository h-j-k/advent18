package com.ikueb.advent18

import com.ikueb.advent18.Day23.getMostInRange
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day23Test {

    @Test
    fun partOneExample() {
        assertThat(getMostInRange(example)).isEqualTo(7)
    }

    @Test
    fun partOne() {
        assertThat(getMostInRange(getInput("Day23"))).isEqualTo(481)
    }

    private val example = listOf(
            "pos=<0,0,0>, r=4",
            "pos=<1,0,0>, r=1",
            "pos=<4,0,0>, r=3",
            "pos=<0,2,0>, r=1",
            "pos=<0,5,0>, r=3",
            "pos=<0,0,3>, r=1",
            "pos=<1,1,1>, r=1",
            "pos=<1,1,2>, r=1",
            "pos=<1,3,1>, r=1"
    )
}