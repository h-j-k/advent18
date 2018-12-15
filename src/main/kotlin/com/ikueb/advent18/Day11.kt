package com.ikueb.advent18

import com.ikueb.advent18.Day11.range

object Day11 {

    val range = (1..300)
    private val innerRange = (2..299)

    fun getTopSquareTopCorner(input: Int) =
            with(generateGrid(input)) {
                filter { (point, _) -> point.x in innerRange && point.y in innerRange }
                        .mapValues { (_, cell) -> cell.withPowerSum(this) }
                        .maxBy { entry -> entry.value }!!
                        .let { (point, _) -> Point(point.x - 1, point.y - 1) }
            }

    private fun generateGrid(serial: Int) = range
            .flatMap { x -> range.map { y -> Point(x, y) } }
            .associateWith { Cell(it.x, it.y, serial) }
}

private data class Cell(val point: Point, val powerLevel: Int) {

    constructor(x: Int, y: Int, serial: Int) :
            this(Point(x, y), (x + 10).let {
                ((((it * y) + serial) * it / 100) % 10) - 5
            })

    fun withPowerSum(grid: Map<Point, Cell>) =
            getNearbyPoints().sumBy { grid[it]!!.powerLevel }

    private fun getNearbyPoints() =
            (-1..1).flatMap { x ->
                (-1..1).map { y ->
                    Point((point.x + x).coerceIn(range),
                            (point.y + y).coerceIn(range))
                }
            }
}