package com.ikueb.advent18

import com.ikueb.advent18.Day11.range

object Day11 {

    val range = (1..300)
    private val subRange = (2..299)

    fun getTopSquareTopCorner(input: Int) =
            with(generateGrid(input)) {
                filterKeys { it.x in subRange && it.y in subRange }
                        .mapValues { (_, cell) -> cell.withNearbyPowerSum(this) }
                        .maxBy { entry -> entry.value }!!
                        .let { (point, _) -> Point(point.x - 1, point.y - 1) }
            }

    fun getTopVariableSquareTopCornerSize(input: Int) =
            generateGrid(input).entries
                    .fold(mutableMapOf<Point, Int>()) { grid, entry ->
                        grid[entry.key] = entry.value.withCumulativeSum(grid); grid
                    }.run {
                        keys.asSequence().flatMap {
                            it.squareSizesTo().map { size ->
                                Triple(it.nw(size - 1), size, squarePowerTo(this, it, size))
                            }
                        }.maxBy { (_, _, power) -> power }!!
                                .let { (corner, size, _) ->
                                    String.format("%d,%d,%d", corner.x, corner.y, size)
                                }
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

    fun withNearbyPowerSum(grid: Map<Point, Cell>) =
            getNearbyPoints().sumBy { grid[it]?.value ?: 0 }

    fun withCumulativeSum(grid: Map<Point, Int>) =
            value + setOf(point.n(), point.w()).sumBy {
                grid[it] ?: 0
            } - (grid[point.nw()] ?: 0)

    private fun getNearbyPoints() =
            (-1..1).flatMap { x ->
                (-1..1).map { y ->
                    Point((point.x + x).coerceIn(range),
                            (point.y + y).coerceIn(range))
                }
            }
}

private fun Point.squareSizesTo() = (1..minOf(x, y)).asSequence()