package com.ikueb.advent18

import com.ikueb.advent18.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class InputMapTest {

    @Test
    fun canRender() {
        val mutable = MutableInputLine(
                InputLine(setOf(TestToken(Point(1, 0), "C")), ""),
                mutableSetOf(TestToken(Point(1, 1), "D")))
        assertThat(mutable.at(1)).isEqualTo('D')
        val map: InputMap<TestToken> = listOf(
                InputLine(setOf(
                        TestToken(Point(0, 0), "A"),
                        TestToken(Point(0, 1), "B")), ""),
                mutable)
        assertThat(map.render()).contains(
                "AB",
                "CD")
    }

}

private data class TestToken(override var point: Point, val value: String) :
        InputToken(point) {

    override fun isActive() = true

    override fun toString() = value
}