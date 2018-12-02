package com.ikueb.advent18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class Day02Test {
    private val example = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
    )

    @Test
    fun example() {
        Assertions.assertThat(getChecksum(example)).isEqualTo(12)
    }

    @Test
    fun partOne() {
        Assertions.assertThat(getChecksum(getInput("Day02"))).isEqualTo(4693)
    }
}