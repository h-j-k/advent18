package com.ikueb.advent18

import com.ikueb.advent18.Day02.getChecksum
import com.ikueb.advent18.Day02.getCommonLetters
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class Day02Test {

    @Test
    fun partOneExample() {
        Assertions.assertThat(getChecksum(partOneExample)).isEqualTo(12)
    }

    @Test
    fun partOne() {
        Assertions.assertThat(getChecksum(getInput("Day02"))).isEqualTo(4693)
    }

    @Test
    fun partTwoExample() {
        Assertions.assertThat(getCommonLetters(partTwoExample)).isEqualTo("fgij")
    }

    @Test
    fun partTwo() {
        Assertions.assertThat(getCommonLetters(getInput("Day02"))).isEqualTo("pebjqsalrdnckzfihvtxysomg")
    }

    private val partOneExample = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
    )

    private val partTwoExample = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
    )
}