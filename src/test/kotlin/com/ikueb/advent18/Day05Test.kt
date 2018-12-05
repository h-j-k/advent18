package com.ikueb.advent18

import com.ikueb.advent18.Day05.getResultingLength
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun canGetResultingLength() {
        assertThat(getResultingLength("dabAcCaCBAcCcaDA")).isEqualTo(10)
    }

    @Test
    fun partOne() {
        val input = getInput("Day05a").joinToString("")
        assertThat(getResultingLength(input)).isEqualTo(10804)
    }
}