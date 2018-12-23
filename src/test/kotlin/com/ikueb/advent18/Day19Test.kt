package com.ikueb.advent18

import com.ikueb.advent18.Day19.getValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day19Test {

    @Test
    fun partOneExample() {
        assertThat(getValue(example)).isEqualTo(6)
    }

    @Test
    fun partOne() {
        assertThat(getValue(getInput("Day19"))).isEqualTo(1764)
    }

    @Test
    fun partTwo() {
        assertThat(getValue(getInput("Day19"), mutableListOf(1, 0, 0, 0, 0, 0)))
                .isEqualTo(18992484)
    }

    private val example = listOf(
            "#ip 0",
            "seti 5 0 1",
            "seti 6 0 2",
            "addi 0 1 0",
            "addr 1 2 3",
            "setr 1 0 0",
            "seti 8 0 4",
            "seti 9 0 5"
    )
}