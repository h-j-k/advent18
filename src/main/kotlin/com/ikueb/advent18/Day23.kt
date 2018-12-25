package com.ikueb.advent18

import com.ikueb.advent18.model.Point3d

object Day23 {

    private const val DEFINITION = "pos=<([-\\d]+),([-\\d]+),([-\\d]+)>, r=(\\d+)"

    fun getMostInRange(input: List<String>): Int {
        val nanobots = input.parseWith(DEFINITION) { (x, y, z, r) ->
            Nanobot(Point3d(x, y, z), r.toInt())
        }
        return nanobots.maxBy { it.radius }
                .let { bot -> nanobots.count { bot!!.inRangeTo(it) } }
    }
}

private data class Nanobot(val point: Point3d, val radius: Int) {

    fun inRangeTo(other: Nanobot) = point.manhattanDistance(other.point) <= radius
}