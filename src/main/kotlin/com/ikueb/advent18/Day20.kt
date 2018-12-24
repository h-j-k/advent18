package com.ikueb.advent18

import com.ikueb.advent18.model.Point
import java.util.*

object Day20 {

    private val movements = mapOf(
            'N' to Point::n,
            'E' to Point::e,
            'S' to Point::s,
            'W' to Point::w
    )

    fun getPassMostDoors(input: String) =
            process(input).maxBy { it.value }!!.value

    fun getPassThrough(input: String, min: Int = 1000) =
            process(input).count { it.value >= min }

    private fun process(input: String): MutableMap<Point, Int> {
        var current = Point(0, 0)
        val grid = mutableMapOf(current to 0)
        val stack = ArrayDeque<Point>()
        input.forEach {
            when (it) {
                '(' -> stack.push(current)
                ')' -> current = stack.pop()
                '|' -> current = stack.peek()
                in movements -> {
                    val nextDistance = grid.getValue(current) + 1
                    current = movements.getValue(it).invoke(current, 1)
                    grid[current] = minOf(grid.getOrDefault(current, Int.MAX_VALUE),
                            nextDistance)
                }
            }
        }
        return grid
    }
}