package com.ikueb.advent18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun canParseFromString() {
        val actual = "(\\d+),(\\d+)".parseWith("1,2") { (x, y) -> Point(x, y) }
        assertThat(actual).isEqualTo(Point(1, 2))
    }

    @Test
    fun canParseFromList() {
        val actual = listOf("1,2").parseWith("(\\d+),(\\d+)") { (x, y) -> Point(x, y) }
        assertThat(actual).contains(Point(1, 2))
    }

    @Test
    fun canMergeSetValues() {
        val map = mutableMapOf<String, Set<Point>>()
        map.mergeSetValues("a", Point(1, 2))
        map.mergeSetValues("a", Point(1, 2))
        map.mergeSetValues("a", Point(3, 4))
        assertThat(map).isEqualTo(mapOf("a" to setOf(Point(1, 2), Point(3, 4))))
    }

    @Test
    fun canFlip() {
        val map = mapOf("a" to 1, "b" to 1)
        assertThat(map.flip()).isEqualTo(mapOf(1 to listOf("a", "b")))
    }
}