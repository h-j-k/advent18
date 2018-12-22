package com.ikueb.advent18.model

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    constructor(x: String, y: String) : this(x.trim().toInt(), y.trim().toInt())

    val adjacent: Set<Point> by lazy { setOf(nw(), n(), ne(), w(), e(), sw(), s(), se()) }

    fun manhattanDistance(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)
    fun nw(offset: Int = 1) = Point(x - offset, y - offset)
    fun n(offset: Int = 1) = Point(x, y - offset)
    fun w(offset: Int = 1) = Point(x - offset, y)
    fun s(offset: Int = 1) = Point(x, y + offset)
    fun e(offset: Int = 1) = Point(x + offset, y)
    fun ne(offset: Int = 1) = Point(x + offset, y - offset)
    fun sw(offset: Int = 1) = Point(x - offset, y + offset)
    fun se(offset: Int = 1) = Point(x + offset, y + offset)

    override fun compareTo(other: Point) =
            compareBy<Point>({ it.y }, { it.x }).compare(this, other)
}