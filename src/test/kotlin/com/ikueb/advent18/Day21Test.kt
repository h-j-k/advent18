package com.ikueb.advent18

import com.ikueb.advent18.Day21.getHaltingValueForFewest
import com.ikueb.advent18.Day21.getHaltingValueForMost
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day21Test {

    @Test
    fun partOne() {
        assertThat(getHaltingValueForFewest(getInput("Day21"))).isEqualTo(15690445)
    }

    @Test
    fun partTwo() {
        assertThat(getHaltingValueForMost(getInput("Day21"))).isEqualTo(936387)
    }
}