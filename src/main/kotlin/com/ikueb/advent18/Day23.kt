package com.ikueb.advent18

import com.ikueb.advent18.model.Point3d
import kotlin.math.abs

object Day23 {

    private const val DEFINITION = "pos=<([-\\d]+),([-\\d]+),([-\\d]+)>, r=(\\d+)"

    fun getMostInRange(input: List<String>) = with(nanobots(input)) {
        count { maxBy { bot -> bot.radius }!!.inRangeTo(it) }
    }

    fun getMostCommonAndClosest(input: List<String>): Int {
        val nanobots = nanobots(input)
        var range = maxOf(nanobots.deltaBy { point.x },
                nanobots.deltaBy { point.y }, nanobots.deltaBy { point.z })
        val origin = Point3d(0, 0, 0)
        var candidates = setOf(Nanobot(origin, range))
        while (range > 0) {
            range = (range / 2) + if (range > 2) 1 else 0
            candidates = candidates.flatMap {
                it.point.adjacent(range).map { point -> Nanobot(point, range) }
            }.groupBy { bot -> nanobots.count { bot.inTotalRangeTo(it) } }
                    .maxBy { it.key }!!.value.toSet()
        }
        return candidates.map { it.point.manhattanDistance(origin) }.min()!!
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

private inline fun <T> List<T>.deltaBy(block: T.() -> Int) =
        with(map(block)) { abs((max() ?: 0) - (min() ?: 0)) }