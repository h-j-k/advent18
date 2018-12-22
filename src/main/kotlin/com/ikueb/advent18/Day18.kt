package com.ikueb.advent18

import com.ikueb.advent18.model.*

object Day18 {

    fun getResourceValue(input: List<String>): Int {
        val state: Area = input.mapIndexed { y, line ->
            InputLine(line.mapIndexed { x, value ->
                Acre(Point(x, y), Content.parse(value))
            }.toSet(), "")
        }
        val boundary = getBoundary(state.getTokens().map { it.point })
        for (i in 1..10) {
            state.getTokens()
                    .associateBy { it.point }
                    .mapValues { (_, acre) -> next(state, boundary, acre) }
                    .forEach { point, content -> state.get(point).content = content }
        }
        return state.getTokens()
                .groupBy { it.content }
                .let { it[Content.TREES]!!.size * it[Content.LUMBERYARD]!!.size }
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
}

private enum class Content {
    OPEN,
    TREES,
    LUMBERYARD;

    companion object {
        fun parse(value: Char) = when (value) {
            '.' -> OPEN
            '|' -> TREES
            '#' -> LUMBERYARD
            else -> throw IllegalArgumentException("Unknown content.")
        }
    }
}