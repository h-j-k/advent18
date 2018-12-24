package com.ikueb.advent18

import com.ikueb.advent18.Day21.getHaltingValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day21Test {

    @Test
    fun partOne() {
        assertThat(getHaltingValue(getInput("Day21"))).isEqualTo(15690445)
    }
}