package com.ikueb.advent18

import com.ikueb.advent18.model.Point
import java.util.*

object Day22 {

    fun getRiskLevel(depth: Int, target: Point) = HardCave(depth, target).apply {
        (0..target.y).forEach { y ->
            (0..target.x).forEach { x -> at(Point(x, y)) }
        }
    }.getTotalRiskRiskLevel()


    fun getShortestTimeTo(depth: Int, target: Point): Int {
        val cave = HardCave(depth, target)
        val start = cave.at(Point(0, 0)).let { CaveStep(it, it.toolsRequired().first()) }
        val seenShortest = mutableMapOf(start.asKey to start.cost)
        val stepsRemaining = PriorityQueue<CaveStep>().apply { add(start) }
        while (stepsRemaining.isNotEmpty()) {
            with(stepsRemaining.poll()) {
                if (at.point == target && using == Tool.TORCH) {
                    return cost
                }
                generateSteps(cave).forEach {
                    val existing = seenShortest[it.asKey]
                    seenShortest.merge(it.asKey, it.cost) { known, current ->
                        minOf(known, current)
                    }.let { updated -> if (existing != updated) stepsRemaining += it }
                }
            }
        }
        return -1
    }
}

private data class Region(val point: Point, val cave: HardCave) {

    val geologicIndex: Int by lazy { deriveGeologicIndex() }
    val erosionLevel: Int by lazy { (geologicIndex + cave.depth) % 20183 }
    val regionType: RegionType by lazy { RegionType.values()[erosionLevel % 3] }
    val riskLevel: Int by lazy { regionType.riskLevel }

    private val atEnds: Boolean by lazy { point in setOf(Point(0, 0), cave.target) }

    private fun deriveGeologicIndex() = when {
        atEnds -> 0
        point.y == 0 -> point.x * 16807
        point.x == 0 -> point.y * 48271
        else -> cave.at(point.w()).erosionLevel * cave.at(point.n()).erosionLevel
    }

    fun toolsRequired(): Set<Tool> = if (atEnds) setOf(Tool.TORCH) else
        when (regionType) {
            RegionType.ROCKY -> setOf(Tool.CLIMBING_GEAR, Tool.TORCH)
            RegionType.WET -> setOf(Tool.CLIMBING_GEAR, Tool.NEITHER)
            RegionType.NARROW -> setOf(Tool.TORCH, Tool.NEITHER)
        }
}

private data class HardCave(val depth: Int, val target: Point) {

    private val state = mutableMapOf<Point, Region>()

    fun at(point: Point) = state.computeIfAbsent(point) { incoming ->
        Region(incoming, this)
    }

    fun getTotalRiskRiskLevel() = state.values.sumBy { it.riskLevel }
}

private data class CaveStep(val at: Region, val using: Tool, val cost: Int = 0) :
        Comparable<CaveStep> {

    val asKey: Pair<Region, Tool> by lazy { at to using }

    override fun compareTo(other: CaveStep) = cost.compareTo(other.cost)

    fun generateSteps(cave: HardCave) = stepSameTools(cave).union(stepOtherTools())

    private fun stepSameTools(cave: HardCave) = at.point.orderedCardinal
            .filter { it.isPositive() }
            .map { Region(it, cave) }
            .filter { using in it.toolsRequired() }
            .map { CaveStep(it, using, cost + 1) }

    private fun stepOtherTools() = at.toolsRequired().minus(using)
            .map { CaveStep(at, it, cost + 7) }
}

private enum class RegionType {
    ROCKY,
    WET,
    NARROW;

    val riskLevel = ordinal
}

private enum class Tool {
    TORCH,
    CLIMBING_GEAR,
    NEITHER
}