package com.ikueb.advent18

import com.ikueb.advent18.Day09.getHighestScore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day09Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "9 players; last marble is worth 25 points | 32",
        "10 players; last marble is worth 1618 points | 8317",
        "13 players; last marble is worth 7999 points | 146373",
        "17 players; last marble is worth 1104 points | 2764",
        "21 players; last marble is worth 6111 points | 54718",
        "30 players; last marble is worth 5807 points | 37305"
    ])
    fun partOneExample(input: String, expected: Long) {
        assertThat(getHighestScore(input)).isEqualTo(expected)
    }

    @Test
    fun partOne() {
        assertThat(getHighestScore("477 players; last marble is worth 70851 points"))
                .isEqualTo(374690)
    }

    @Test
    fun partTwo() {
        assertThat(getHighestScore("477 players; last marble is worth 7085100 points"))
                .isEqualTo(3009951158)
    }
}