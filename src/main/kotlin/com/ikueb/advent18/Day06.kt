package com.ikueb.advent18

import com.ikueb.advent18.model.Point
import com.ikueb.advent18.model.isOn
import com.ikueb.advent18.model.getBoundary
import com.ikueb.advent18.model.getEnclosingPoints

object Day06 {

    fun getLargestDefiniteArea(input: List<String>): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        val boundary = getBoundary(points)
        return boundary.getEnclosingPoints()
                .associateWith { point ->
                    points.associateWith(point::manhattanDistance)
                            .flip()
                            .minBy { it.key }
                            .let { if (it?.value?.size == 1) it.value[0] else null }
                }
                .filterValues { it != null }
                .flip()
                .filterValues { closest -> closest.none(boundary::isOn) }
                .map { it.value.size }
                .maxBy { it }
                ?: throw IllegalArgumentException("No results.")
    }

    fun getLargestRegionWithManhattanDistanceSumLessThan(input: List<String>, sum: Int): Int {
        val points = input.parseWith("(\\d+), (\\d+)") { (x, y) -> Point(x, y) }
        return getBoundary(points).getEnclosingPoints()
                .associateWith { point -> points.sumBy(point::manhattanDistance) }
                .filterValues { it < sum }
                .size
    }
}