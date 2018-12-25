package com.ikueb.advent18.model

data class Point3d(val x: Int, val y: Int, val z: Int) {

    constructor(x: String, y: String, z: String) : this(x.toInt(), y.toInt(), z.toInt())

    fun manhattanDistance(other: Point3d) =
            listOf(x - other.x, y - other.y, z - other.z).sumBy { Math.abs(it) }
}