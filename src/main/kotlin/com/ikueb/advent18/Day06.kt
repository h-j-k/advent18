package com.ikueb.advent18

import kotlin.properties.Delegates.vetoable

object Day06 {

    fun getLargestDefiniteArea(input: List<String>): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        val boundaries = getBoundaries(points)
        return generatePoints(boundaries)
                .associateWith { point ->
                    points.associateWith(point::manhattanDistance)
                            .flip()
                            .minBy { it.key }
                            .let { if (it?.value?.size == 1) it.value[0] else null }
                }
                .filterValues { it != null }
                .flip()
                .filterValues { closest ->
                    closest.none { boundaries.let { (min, max) -> min.on(it) || max.on(it) } }
                }
                .map { it.value.size }
                .maxBy { it }!!
    }

    fun getLargestRegionWithManhattanDistanceSumLessThan(input: List<String>, sum: Int): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        return generatePoints(getBoundaries(points))
                .associateWith { point -> points.sumBy(point::manhattanDistance) }
                .filterValues { it < sum }
                .size
    }

    private fun getBoundaries(points: Collection<Point>): Pair<Point, Point> {
        var x1: Int by vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
        var x2: Int by vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
        var y1: Int by vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
        var y2: Int by vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
        points.forEach {
            x1 = it.x
            x2 = it.x
            y1 = it.y
            y2 = it.y
        }
        return Point(x1, y1) to Point(x2, y2)
    }

    private fun generatePoints(boundaries: Pair<Point, Point>): Set<Point> {
        val (min, max) = boundaries
        return (min.x..max.x)
                .flatMap { x -> (min.y..max.y).map { y -> Point(x, y) } }
                .toSet()
    }
}

private fun Point.on(other: Point) = x == other.x || y == other.y

private fun Point.manhattanDistance(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)