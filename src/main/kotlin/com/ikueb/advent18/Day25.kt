package com.ikueb.advent18

import java.util.*

object Day25 {

    private const val DEFINITION = "([-\\d ]+),([-\\d ]+),([-\\d ]+),([-\\d ]+)"

    fun getConstellations(input: List<String>) = with(
            input.parseWith(DEFINITION) { (a, b, c, d) -> Point4d(a, b, c, d) }) {
        associateWith { point -> filter { point.isNearby(it) }.toSet() }
    }.let { formConstellations(it).count() }

    private fun formConstellations(nearby: Map<Point4d, Set<Point4d>>) = sequence {
        val remaining = nearby.keys.toCollection(ArrayDeque<Point4d>())
        while (remaining.isNotEmpty()) {
            val (current, toProcess) = with(remaining.removeFirst()) {
                mutableSetOf(this) to ArrayDeque<Point4d>(nearby[this]!!)
            }
            while (toProcess.isNotEmpty()) {
                toProcess.removeFirst().let { point ->
                    remaining.remove(point)
                    current.add(point)
                    toProcess.addAll(nearby[point]!!.filterNot { it in current })
                }
            }
            yield(current)
        }
    }
}

private data class Point4d(val a: Int, val b: Int, val c: Int, val d: Int) {

    constructor(a: String, b: String, c: String, d: String) :
            this(a.trim().toInt(), b.trim().toInt(), c.trim().toInt(), d.trim().toInt())

    fun isNearby(other: Point4d) = this != other && manhattanDistance(other) <= 3

    private fun manhattanDistance(other: Point4d) =
            listOf(a - other.a, b - other.b, c - other.c, d - other.d)
                    .sumBy { Math.abs(it) }
}