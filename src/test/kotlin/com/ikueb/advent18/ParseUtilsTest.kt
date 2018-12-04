package com.ikueb.advent18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ParseUtilsTest {

    @Test
    fun canParseFromString() {
        val actual = "(\\d+),(\\d+)".parseWith("1,2") { (x, y) -> Point(x.toInt(), y.toInt()) }
        assertThat(actual).isEqualTo(Point(1, 2))
    }

    @Test
    fun canParseFromList() {
        val actual = listOf("1,2").parseWith("(\\d+),(\\d+)") { (x, y) -> Point(x.toInt(), y.toInt()) }
        assertThat(actual).contains(Point(1, 2))
    }
}

private data class Point(val x: Int, val y: Int)