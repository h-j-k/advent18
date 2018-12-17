package com.ikueb.advent18

import com.ikueb.advent18.model.Point
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
        val map = mutableMapOf<String, Set<Int>>()
        map.mergeSetValues("a", 1)
        map.mergeSetValues("a", 1)
        map.mergeSetValues("a", 2)
        assertThat(map).isEqualTo(mapOf("a" to setOf(1, 2)))
    }

    @Test
    fun canMergeListValues() {
        val map = mutableMapOf<String, MutableList<Int>>()
        map.mergeMutableListValues("a", 1)
        map.mergeMutableListValues("a", 1)
        map.mergeMutableListValues("a", 2)
        assertThat(map).isEqualTo(mapOf("a" to listOf(1, 1, 2)))
    }

    @Test
    fun canFlip() {
        val map = mapOf("a" to 1, "b" to 1)
        assertThat(map.flip()).isEqualTo(mapOf(1 to listOf("a", "b")))
    }
}