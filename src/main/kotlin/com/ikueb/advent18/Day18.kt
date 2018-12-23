package com.ikueb.advent18

import com.ikueb.advent18.model.*

object Day18 {

    fun getResourceValue(input: List<String>, iterations: Int): Int {
        val state: Area = input.mapIndexed { y, line ->
            InputLine(line.mapIndexed { x, value ->
                Acre(Point(x, y), Content.parse(value))
            }.toSet(), "")
        }
        val seen = mutableMapOf<Int, Int>()
        var matched: Int?
        do {
            state.step()
            matched = seen.put(state.render().hashCode(), seen.size)
        } while (matched == null && seen.size < iterations)
        if (seen.size < iterations) {
            val current = seen[state.render().hashCode()]!!
            repeat(((iterations - matched!!) % (current - matched)) - 1) {
                state.step()
            }
        }
        return state.getTokens().groupBy { it.content }
                .let { it[Content.TREES]!!.size * it[Content.LUMBERYARD]!!.size }
    }
}

private typealias Area = InputMap<Acre>

private fun Area.step() = getTokens()
        .associateBy { it.point }
        .mapValues { (_, acre) -> acre.next(this) }
        .forEach { point, content -> get(point)?.content = content }

private fun Area.near(acre: Acre, min: Int, target: Content) =
        acre.point.adjacent.count { get(it)?.content == target } >= min

private fun Area.get(point: Point) =
        if (point.y in 0..(size - 1)) {
            this[point.y].tokens().firstOrNull { it.atColumn(point.x) }
        } else null

private data class Acre(
        override var point: Point,
        var content: Content) : InputToken(point) {

    override fun isActive() = true

    override fun toString() = content.output.toString()

    fun next(state: Area) = when (content) {
        Content.OPEN ->
            if (state.near(this, 3, Content.TREES)) Content.TREES
            else content
        Content.TREES ->
            if (state.near(this, 3, Content.LUMBERYARD)) Content.LUMBERYARD
            else content
        Content.LUMBERYARD ->
            if (state.near(this, 1, Content.LUMBERYARD)
                    && state.near(this, 1, Content.TREES)) Content.LUMBERYARD
            else Content.OPEN
    }
}

private enum class Content(val output: Char) {
    OPEN('.'),
    TREES('|'),
    LUMBERYARD('#');

    companion object {
        fun parse(value: Char) = values().find { it.output == value }
                ?: throw IllegalArgumentException("Unknown content.")
    }
}