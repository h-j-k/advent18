package com.ikueb.advent18

import com.ikueb.advent18.model.Point

object Day22 {

    fun getRiskLevel(depth: Int, target: Point): Int {
        val cave = HardCave(depth, target)
        for (y in (0..target.y)) {
            for (x in (0..target.x)) {
                Region(Point(x, y), cave).let { cave.add(it) }
            }
        }
        return cave.getTotalRiskRiskLevel()
    }
}

private data class Region(val point: Point, val cave: HardCave) {

    val geologicIndex: Int by lazy { deriveGeologicIndex() }
    val erosionLevel: Int by lazy { (geologicIndex + cave.depth) % 20183 }
    val regionType: RegionType by lazy { RegionType.values()[erosionLevel % 3] }

    private fun deriveGeologicIndex() = when {
        point in setOf(Point(0, 0), cave.target) -> 0
        point.y == 0 -> point.x * 16807
        point.x == 0 -> point.y * 48271
        else -> cave.at(point.w()).erosionLevel * cave.at(point.n()).erosionLevel
    }

    fun getRiskLevel() = regionType.getRiskLevel()
}

private data class HardCave(val depth: Int, val target: Point) {

    private val state = mutableMapOf<Point, Region>()

    fun add(region: Region) {
        state[region.point] = region
    }

    fun at(point: Point) = state[point]!!

    fun getTotalRiskRiskLevel() = state.values.sumBy { it.getRiskLevel() }
}

private enum class RegionType {
    ROCKY,
    WET,
    NARROW;

    fun getRiskLevel() = ordinal
}