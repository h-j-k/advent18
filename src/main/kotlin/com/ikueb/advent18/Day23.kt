package com.ikueb.advent18

import com.ikueb.advent18.model.Point3d
import kotlin.math.abs
import kotlin.math.max

object Day23 {

    private const val DEFINITION = "pos=<([-\\d]+),([-\\d]+),([-\\d]+)>, r=(\\d+)"

    fun getMostInRange(input: List<String>) = with(nanobots(input)) {
        count { maxBy { bot -> bot.radius }!!.inRangeTo(it) }
    }

    fun getMostCommonAndClosest(input: List<String>): Int {
        val nanobots = nanobots(input)
        val origin = Point3d(0, 0, 0)
        var currentRadius = max(nanobots.deltaBy { point.x },
                max(nanobots.deltaBy { point.y }, nanobots.deltaBy { point.z }))
        var currentBots = setOf(Nanobot(origin, currentRadius))
        while (currentRadius > 0) {
            currentRadius = (currentRadius / 2) + if (currentRadius > 2) 1 else 0
            val next = currentBots.flatMap { bot ->
                bot.point.adjacent(currentRadius).map { point ->
                    Nanobot(point, currentRadius)
                            .let { bot ->
                                bot to nanobots.count { bot.inTotalRangeTo(it) }
                            }
                }
            }
            val maxDistance = next.map { it.second }.max() ?: 0
            currentBots = next.filter { it.second == maxDistance }
                    .map { it.first }
                    .toSet()
        }
        return currentBots.minBy { origin.manhattanDistance(it.point) }!!.point.manhattanDistance(origin)
    }

    private fun nanobots(input: List<String>) =
            input.parseWith(DEFINITION) { (x, y, z, r) ->
                Nanobot(Point3d(x, y, z), r.toInt())
            }
}

private data class Nanobot(val point: Point3d, val radius: Int) {

    fun inRangeTo(other: Nanobot) = point.manhattanDistance(other.point) <= radius

    fun inTotalRangeTo(other: Nanobot) =
            point.manhattanDistance(other.point) <= radius + other.radius
}

private inline fun <T> Iterable<T>.deltaBy(block: T.() -> Int): Int {
    val values = map(block)
    return abs((values.max() ?: 0) - (values.min() ?: 0))
}