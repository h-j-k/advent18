package com.ikueb.advent18

import com.ikueb.advent18.model.*

object Day18 {

    fun getResourceValue(input: List<String>, iterations: Int): Int {
        val state: Area = input.mapIndexed { y, line ->
            InputLine(line.mapIndexed { x, value ->
                Acre(Point(x, y), Content.parse(value))
            }.toSet(), "")
        }
        val boundary = getBoundary(state.getTokens().map { it.point })
        val seen = mutableMapOf<Int, Int>()
        var matched: Int?
        do {
            step(state, boundary)
            matched = seen.put(state.render().hashCode(), seen.size)
        } while (matched == null && seen.size < iterations)
        if (seen.size < iterations) {
            val current = seen[state.render().hashCode()]!!
            repeat(((iterations - matched!!) % (current - matched)) - 1) {
                step(state, boundary)
            }
        }
        return state.getTokens()
                .groupBy { it.content }
                .let { it[Content.TREES]!!.size * it[Content.LUMBERYARD]!!.size }
    }

    private fun step(state: Area, boundary: Boundary) {
        state.getTokens()
                .associateBy { it.point }
                .mapValues { (_, acre) -> next(state, boundary, acre) }
                .forEach { point, content -> state.get(point).content = content }
    }

    private fun next(state: Area,
                     boundary: Boundary,
                     acre: Acre) = when (val now = acre.content) {
        Content.OPEN ->
            if (matches(state, boundary, acre, 3, Content.TREES)) Content.TREES
            else now
        Content.TREES ->
            if (matches(state, boundary, acre, 3, Content.LUMBERYARD)) Content.LUMBERYARD
            else now
        Content.LUMBERYARD ->
            if (matches(state, boundary, acre, 1, Content.LUMBERYARD)
                    && matches(state, boundary, acre, 1, Content.TREES)) Content.LUMBERYARD
            else Content.OPEN
    }

    private fun matches(state: Area,
                        boundary: Boundary,
                        acre: Acre,
                        min: Int,
                        targetContent: Content) =
            acre.point.adjacent
                    .filter { boundary.contains(it) }
                    .count { state.get(it).content == targetContent } >= min

}

private typealias Area = InputMap<Acre>

private fun Area.get(point: Point) =
        this[point.y].tokens().first { it.atColumn(point.x) }

private data class Acre(
        override var point: Point,
        var content: Content) : InputToken(point) {

    override fun isActive() = true

    override fun toString() = content.output.toString()
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