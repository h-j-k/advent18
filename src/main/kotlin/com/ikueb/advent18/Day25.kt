package com.ikueb.advent18

import java.util.*

object Day25 {

    private const val DEFINITION = "([-\\d ]+),([-\\d ]+),([-\\d ]+),([-\\d ]+)"

    fun getConstellations(input: List<String>) = with(
            input.parseWith(DEFINITION) { (a, b, c, d) ->
                Point4d(a.trimInt(), b.trimInt(), c.trimInt(), d.trimInt())
            }) {
        associateWith { point -> filter { point.isNearby(it) }.toSet() }
    }.let { formConstellations(it).count() }

    private fun formConstellations(nearby: Map<Point4d, Set<Point4d>>) = sequence {
        val remaining = nearby.keys.toDeque()
        while (remaining.isNotEmpty()) {
            val (current, toProcess) = with(remaining.removeFirst()) {
                mutableSetOf(this) to nearby[this]!!.toDeque()
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

    fun isNearby(other: Point4d) = this != other && manhattanDistance(other) <= 3

    private fun manhattanDistance(other: Point4d) =
            listOf(a - other.a, b - other.b, c - other.c, d - other.d)
                    .sumBy { Math.abs(it) }
}

private fun String.trimInt() = trim().toInt()

private fun <T> Iterable<T>.toDeque() = toCollection(ArrayDeque<T>())