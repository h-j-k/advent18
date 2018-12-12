package com.ikueb.advent18

import com.ikueb.advent18.Day08.getSumOfMetadata
import com.ikueb.advent18.Day08.getValueOfRootNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day08Test {

    @Test
    fun partOneExample() {
        assertThat(getSumOfMetadata(example)).isEqualTo(138)
    }

    @Test
    fun partOne() {
        val input = getSingleInput("Day08")
        assertThat(getSumOfMetadata(input)).isEqualTo(42768)
    }

    @Test
    fun partTwoExample() {
        assertThat(getValueOfRootNode(example)).isEqualTo(66)
    }

    @Test
    fun partTwo() {
        val input = getSingleInput("Day08")
        assertThat(getValueOfRootNode(input)).isEqualTo(34348)
    }

    private val example = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
}