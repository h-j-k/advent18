package com.ikueb.advent18

object Day10 {

    private const val POSITION = "position=<([^,]+),([^>]+)> velocity=<([^,]+),([^>]+)>"

    fun partOne(input: List<String>) = process(input).first

    fun partTwo(input: List<String>) = process(input).second

    private fun process(input: List<String>): Pair<List<String>, Int> {
        val points = input.parseWith(POSITION)
        { (x, y, xVelocity, yVelocity) ->
            MovingPoint(Point(x, y), xVelocity.trim().toInt() to yVelocity.trim().toInt())
        }
        var answer: Boundary? = null
        for (i in generateSequence(1) { it + 1 }) {
            val current = getMap(points)
            if (current.getArea() < (answer?.getArea() ?: Long.MAX_VALUE)) {
                answer = current
                continue
            }
            val output = with(answer!!) {
                Array(getHeight()) {
                    StringBuilder().apply {
                        append(CharArray(getWidth()) { '.' })
                    }
                }
            }
            points.map(MovingPoint::reverse).forEach {
                output[it.y - answer.getOriginY()][it.x - answer.getOriginX()] = '#'
            }
            return output.map { it.toString() } to i - 1
        }
        throw IllegalStateException("No results.")
    }

    private fun getMap(points: Collection<MovingPoint>) =
            getBoundary(points.map(MovingPoint::forward))
}

data class MovingPoint(var point: Point, val velocity: Pair<Int, Int>) {
    fun forward(): Point {
        point = Point(point.x + velocity.first, point.y + velocity.second)
        return point
    }

    fun reverse(): Point {
        point = Point(point.x - velocity.first, point.y - velocity.second)
        return point
    }
}