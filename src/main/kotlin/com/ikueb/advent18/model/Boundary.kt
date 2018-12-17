package com.ikueb.advent18.model

import kotlin.properties.Delegates

typealias Boundary = Pair<Point, Point>

fun getBoundary(points: Collection<Point>): Boundary {
    var xMin: Int by Delegates.vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
    var xMax: Int by Delegates.vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
    var yMin: Int by Delegates.vetoable(Int.MAX_VALUE) { _, old, new -> new < old }
    var yMax: Int by Delegates.vetoable(Int.MIN_VALUE) { _, old, new -> new > old }
    points.forEach {
        xMin = it.x
        xMax = it.x
        yMin = it.y
        yMax = it.y
    }
    return Point(xMin, yMin) to Point(xMax, yMax)
}

fun Boundary.getWidth() = second.x - first.x + 1

fun Boundary.getHeight() = second.y - first.y + 1

fun Boundary.getArea() = getWidth().toLong() * getHeight().toLong()

fun Boundary.getOffset(target: Point) = target.x - first.x to target.y - first.y

fun Boundary.getEnclosingPoints() =
        (first.x..second.x)
                .flatMap { x -> (first.y..second.y).map { y -> Point(x, y) } }
                .toSet()

fun Boundary.contains(point: Point) =
        point.x == first.x || point.x == second.x || point.y == first.y || point.y == second.y
