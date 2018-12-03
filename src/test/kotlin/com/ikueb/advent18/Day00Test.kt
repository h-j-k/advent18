package com.ikueb.advent18

import com.ikueb.advent18.Day00.minus
import com.ikueb.advent18.Day00.sum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day00Test {

    @Test
    fun canAdd() {
        assertThat(sum(1, 2)).isEqualTo(3)
    }

    @Test
    fun canMinus() {
        assertThat(minus(1, 2)).isEqualTo(-1)
    }

    @Test
    fun canGetLines() {
        assertThat(getInput("Day00")).contains("Line 1", "Line 2")
    }

    @Test
    fun canGetSingleLine() {
        assertThat(getSingleInput("Day00")).contains("Line 1")
    }
}