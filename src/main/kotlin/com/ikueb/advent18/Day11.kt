package com.ikueb.advent18

object Day11 {

    private val range = (1..300)
    private val boundary = Boundary(Point(1, 1), Point(300, 300))

    fun getTopSquareTopCorner(input: Int) =
            process(input) { point ->
                if (boundary.isOnBoundary(point)) emptySequence() else sequenceOf(3)
            }.first

    fun getTopVariableSquareTopCornerSize(input: Int) =
            process(input).let { (corner, size, _) ->
                String.format("%d,%d,%d", corner.x, corner.y, size)
            }

    private fun process(input: Int,
                        toSequence: (Point) -> Sequence<Int> = Point::squareSizesTo) =
            generateGrid(input).entries
                    .fold(mutableMapOf<Point, Int>()) { grid, entry ->
                        grid.also { it[entry.key] = entry.value.cumulativeSum(it) }
                    }.let {
                        it.keys.asSequence().flatMap { point ->
                            toSequence(point).map { size ->
                                Triple(point.nw(size - 1), size,
                                        squarePowerTo(it, point, size))
                            }
                        }.maxBy { (_, _, power) -> power }!!
                    }

    private fun generateGrid(serial: Int) = range
            .flatMap { x -> range.map { y -> Point(x, y) } }
            .associateWith { Cell(it.x, it.y, serial) }

    private fun squarePowerTo(grid: Map<Point, Int>, pointTo: Point, size: Int) =
            setOf(pointTo, pointTo.nw(size)).sumBy {
                grid[it] ?: 0
            } - setOf(pointTo.n(size), pointTo.w(size)).sumBy {
                grid[it] ?: 0
            }
}

private data class Cell(val point: Point, val value: Int) {

    constructor(x: Int, y: Int, serial: Int) :
            this(Point(x, y), (x + 10).let {
                ((((it * y) + serial) * it / 100) % 10) - 5
            })

    fun cumulativeSum(grid: Map<Point, Int>) =
            value + setOf(point.n(), point.w()).sumBy {
                grid[it] ?: 0
            } - (grid[point.nw()] ?: 0)
}

private fun Point.squareSizesTo() = (1..minOf(x, y)).asSequence()

private fun Boundary.isOnBoundary(point: Point) = with(point) {
    x == first.x || x == second.y || y == first.y || y == second.y
}