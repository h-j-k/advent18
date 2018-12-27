package com.ikueb.advent18

import com.ikueb.advent18.Day23.getMostCommonAndClosest
import com.ikueb.advent18.Day23.getMostInRange
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day23Test {

    @Test
    fun partOneExample() {
        assertThat(getMostInRange(partOneExample)).isEqualTo(7)
    }

    @Test
    fun partOne() {
        assertThat(getMostInRange(getInput("Day23"))).isEqualTo(481)
    }

    @Test
    fun partTwoExample() {
        assertThat(getMostCommonAndClosest(partTwoExample)).isEqualTo(36)
    }

    @Test
    fun partTwo() {
        assertThat(getMostCommonAndClosest(getInput("Day23"))).isEqualTo(47141479)
    }

    private val partOneExample = listOf(
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

    private val partTwoExample = listOf(
            "pos=<10,12,12>, r=2",
            "pos=<12,14,12>, r=2",
            "pos=<16,12,12>, r=4",
            "pos=<14,14,14>, r=6",
            "pos=<50,50,50>, r=200",
            "pos=<10,10,10>, r=5"
    )
}