package com.ikueb.advent18

import com.ikueb.advent18.Day03.getNonOverlappingId
import com.ikueb.advent18.Day03.getOverlappingRegions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day03Test {

    @Test
    fun partOneExample() {
        assertThat(getOverlappingRegions(partOneExample)).isEqualTo(4)
    }

    @Test
    fun partOne() {
        assertThat(getOverlappingRegions(getInput("Day03"))).isEqualTo(115242)
    }

    @Test
    fun partTwoExample() {
        assertThat(getNonOverlappingId(partOneExample)).isEqualTo(3)
    }

    @Test
    fun partTwo() {
        assertThat(getNonOverlappingId(getInput("Day03"))).isEqualTo(1046)
    }

    private val partOneExample = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
    )
}