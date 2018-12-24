package com.ikueb.advent18

import com.ikueb.advent18.Day22.getRiskLevel
import com.ikueb.advent18.model.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day22Test {

    @Test
    fun partOneExample() {
        assertThat(getRiskLevel(510, Point(10, 10))).isEqualTo(114)
    }

    @Test
    fun partOne() {
        assertThat(getRiskLevel(9171, Point(7, 721))).isEqualTo(5786)
    }
}