package com.ikueb.advent18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day14Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "5 | 0124515891",
        "9 | 5158916779",
        "18 | 9251071085",
        "2018 | 5941429882"
    ])
    fun partOneExample(input: Int, expected: String) {
        assertThat(Day14.getTenRecipeScoresAfter(input)).isEqualTo(expected)
    }

    @Test
    fun partOne() {
        assertThat(Day14.getTenRecipeScoresAfter(509671)).isEqualTo("2810862211")
    }
}