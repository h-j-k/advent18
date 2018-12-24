package com.ikueb.advent18

import com.ikueb.advent18.Day05.getResultingLength
import com.ikueb.advent18.Day05.getShortestPossible
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun partOneExample() {
        assertThat(getResultingLength("dabAcCaCBAcCcaDA")).isEqualTo(10)
    }

    @Test
    fun partOne() {
        val input = getInput("Day05").joinToString("")
        assertThat(getResultingLength(input)).isEqualTo(10804)
    }

    @Test
    fun partTwoExample() {
        assertThat(getShortestPossible("dabAcCaCBAcCcaDA")).isEqualTo(4)
    }

    @Test
    fun partTwo() {
        val input = getInput("Day05").joinToString("")
        assertThat(getShortestPossible(input)).isEqualTo(6650)
    }

    @Test
    fun edgeCases() {
        assertThat(getShortestPossible("")).isEqualTo(0)
        assertThat(getShortestPossible("aA")).isEqualTo(0)
        assertThat(getShortestPossible("ab")).isEqualTo(1)
        assertThat(getShortestPossible(" ")).isEqualTo(1)
    }
}