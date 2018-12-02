package com.ikueb.advent18

import com.ikueb.advent18.Day01.repeatingFrequencyTwice
import com.ikueb.advent18.Day01.resultingFrequency
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day01Test {

    @Test
    fun partOne() {
        assertThat(resultingFrequency(getInput("Day01"))).isEqualTo(435)
    }

    @Test
    fun partTwo() {
        assertThat(repeatingFrequencyTwice(getInput("Day01"))).isEqualTo(245)
    }
}