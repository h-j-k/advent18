package com.ikueb.advent18

object Day10 {

    private const val POSITION = "position=<([^,]+),([^>]+)> velocity=<([^,]+),([^>]+)>"

    fun partOne(input: List<String>) = process(input).first

    fun partTwo(input: List<String>) = process(input).second

    private fun process(input: List<String>): Pair<List<String>, Int> {
        val points = input.parseWith(POSITION)
        { (x, y, xVelocity, yVelocity) -> MovingPoint(x, y, xVelocity, yVelocity) }
        var answer: Boundary? = null
        for (i in generateSequence(1) { it + 1 }) {
            val current = getBoundary(points.map(MovingPoint::forward))
            if (current.getArea() < (answer?.getArea() ?: Long.MAX_VALUE)) {
                answer = current
                continue
            }
            return with(answer!!) {
                Array(getHeight()) {
                    StringBuilder().apply {
                        append(CharArray(getWidth()) { '.' })
                    }
                }
            }.apply {
                points.map { answer.getOffset(it.reverse()) }
                        .forEach { (x, y) -> this[y][x] = '#' }
            }.map { it.toString() } to i - 1
        }
        throw IllegalStateException("No results.")
    }
}

data class MovingPoint(var point: Point, val xVelocity: Int, val yVelocity: Int) {

    constructor(x: String, y: String, xVelocity: String, yVelocity: String) :
            this(Point(x, y), xVelocity.trim().toInt(), yVelocity.trim().toInt())

    fun forward(): Point {
        point = Point(point.x + xVelocity, point.y + yVelocity)
        return point
    }

    fun reverse(): Point {
        point = Point(point.x - xVelocity, point.y - yVelocity)
        return point
    }
}