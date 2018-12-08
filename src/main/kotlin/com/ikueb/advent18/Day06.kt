package com.ikueb.advent18

import kotlin.properties.Delegates.vetoable

object Day06 {

    fun getLargestDefiniteArea(input: List<String>): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        val boundary = getBoundary(points)
        return boundary.getEnclosingPoints()
                .associateWith { point ->
                    points.associateWith(point::manhattanDistance)
                            .flip()
                            .minBy { it.key }
                            .let { if (it?.value?.size == 1) it.value[0] else null }
                }
                .filterValues { it != null }
                .flip()
                .filterValues { closest -> closest.none(boundary::contains) }
                .map { it.value.size }
                .maxBy { it }
                ?: throw IllegalArgumentException("No results.")
    }

    fun getLargestRegionWithManhattanDistanceSumLessThan(input: List<String>, sum: Int): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        return getBoundary(points).getEnclosingPoints()
                .associateWith { point -> points.sumBy(point::manhattanDistance) }
                .filterValues { it < sum }
                .size
    }

    private fun getBoundary(points: Collection<Point>): Boundary {
        var xMin: Int by vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
        var xMax: Int by vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
        var yMin: Int by vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
        var yMax: Int by vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
        points.forEach {
            xMin = it.x
            xMax = it.x
            yMin = it.y
            yMax = it.y
        }
        return Point(xMin, yMin) to Point(xMax, yMax)
    }
}

private typealias Boundary = Pair<Point, Point>

private fun Boundary.getEnclosingPoints() =
        (first.x..second.x)
                .flatMap { x -> (first.y..second.y).map { y -> Point(x, y) } }
                .toSet()

private fun Boundary.contains(point: Point) =
        point.x == first.x || point.x == second.x || point.y == first.y || point.y == second.y

private fun Point.manhattanDistance(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)