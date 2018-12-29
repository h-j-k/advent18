package com.ikueb.advent18

import com.ikueb.advent18.Day25.getConstellations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class Day25Test {

    @ParameterizedTest
    @EnumSource(TestCase::class)
    fun partOneExample(testCase: TestCase) {
        assertThat(getConstellations(testCase.input)).isEqualTo(testCase.expected)
    }

    @Test
    fun partOne() {
        assertThat(getConstellations(getInput("Day25"))).isEqualTo(350)
    }
}

internal enum class TestCase(val input: List<String>, val expected: Int) {
    ONE(listOf(
            " 0,0,0,0",
            " 3,0,0,0",
            " 0,3,0,0",
            " 0,0,3,0",
            " 0,0,0,3",
            " 0,0,0,6",
            " 9,0,0,0",
            "12,0,0,0"
    ), 2),
    TWO(listOf(
            "-1,2,2,0",
            "0,0,2,-2",
            "0,0,0,-2",
            "-1,2,0,0",
            "-2,-2,-2,2",
            "3,0,2,-1",
            "-1,3,2,2",
            "-1,0,-1,0",
            "0,2,1,-2",
            "3,0,0,0"
    ), 4),
    THREE(listOf(
            "1,-1,0,1",
            "2,0,-1,0",
            "3,2,-1,0",
            "0,0,3,1",
            "0,0,-1,-1",
            "2,3,-2,0",
            "-2,2,0,0",
            "2,-2,0,-1",
            "1,-1,0,-1",
            "3,2,0,2"
    ), 3),
    FOUR(listOf(
            "1,-1,-1,-2",
            "-2,-2,0,1",
            "0,2,1,3",
            "-2,3,-2,1",
            "0,2,3,-2",
            "-1,-1,1,-2",
            "0,-2,-1,0",
            "-2,2,3,-1",
            "1,2,2,0",
            "-1,-2,0,-2"
    ), 8)

}