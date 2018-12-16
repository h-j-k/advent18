package com.ikueb.advent18

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

inline fun <T> String.parseWith(input: CharSequence, mapper: (MatchResult.Destructured) -> T) =
        (toRegex().matchEntire(input) ?: throw IllegalArgumentException("Wrong format."))
                .destructured.let(mapper)

inline fun <T> List<CharSequence>.parseWith(pattern: String, mapper: (MatchResult.Destructured) -> T) =
        map { pattern.parseWith(it, mapper) }

fun <K, V> MutableMap<K, Set<V>>.mergeSetValues(key: K, value: V) =
        merge(key, setOf(value)) { a, b -> a.plus(b) }

fun <K, V> MutableMap<K, MutableList<V>>.mergeMutableListValues(key: K, value: V) =
        merge(key, mutableListOf(value)) { a, b -> a.addAll(b); a }

fun <T, R> Iterable<T>.parallelMap(mapper: suspend (T) -> R) =
        runBlocking { map { async { mapper(it) } }.map { it.await() } }

fun <K, V> Map<K, V>.flip() = this.entries
        .groupBy { it.value }
        .mapValues { (_, list) -> list.map { it.key } }

data class Point(val x: Int, val y: Int) {
    constructor(x: String, y: String) : this(x.trim().toInt(), y.trim().toInt())

    fun manhattanDistance(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)
    fun nw(offset: Int = 1) = Point(x - offset, y - offset)
    fun n(offset: Int = 1) = Point(x, y - offset)
    fun w(offset: Int = 1) = Point(x - offset, y)
}

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

fun <T> List<T?>.firstOrNull(): T? {
    forEach { if (it != null) return it }
    return null
}