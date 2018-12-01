package com.ikueb.advent18

import com.ikueb.advent18.Day00.sum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day00KtTest {

    @Test
    fun canAdd() {
        assertThat(sum(1, 2)).isEqualTo(3)
    }

    @Test
    fun canGetLines() {
        assertThat(getInput(Day00.javaClass)).contains("Line 1", "Line 2")
    }

    @Test
    fun canGetSingleLine() {
        assertThat(getSingleInput(Day00.javaClass)).contains("Line 1")
    }
}