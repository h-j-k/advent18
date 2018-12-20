package com.ikueb.advent18

import com.ikueb.advent18.Day16.likelyOpscodes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day16Test {

    @Test
    fun partOneExample() {
        assertThat(likelyOpscodes(partOneExample)).isEqualTo(1)
    }

    @Test
    fun partOne() {
        assertThat(likelyOpscodes(getInput("Day16"))).isEqualTo(560)
    }

    private val partOneExample = listOf(
            "Before: [3, 2, 1, 1]",
            "9 2 1 2",
            "After:  [3, 2, 2, 1]"
    )
}