package com.ikueb.advent18

import com.ikueb.advent18.model.*
import java.util.*

object Day17 {

    private const val DEFINITION = "(.)=(\\d+), .=(\\d+)..(\\d+)"
    private val source = Point(500, 0)

    fun getWaterReach(input: List<String>): Int {
        val state = process(input)
        val yMin = state.getTokens().minBy { it.y() }!!.y()
        return state.flatMap { it.additions }
                .distinctBy { it.point }
                .count { it.y() >= yMin }
    }

    fun getWaterRetained(input: List<String>) = process(input)
            .flatMap { it.additions }
            .count { it is StillWater }

    private fun process(input: List<String>): Ground {
        val veins = input.parseWith(DEFINITION) { (axis, a, b1, b2) ->
            when (axis) {
                "x" -> (b1.toInt()..b2.toInt()).map { b -> Clay(Point(a.toInt(), b)) }
                else -> (b1.toInt()..b2.toInt()).map { b -> Clay(Point(b, a.toInt())) }
            }
        }.flatten()
        val boundary: Boundary = getBoundary(setOf(source).union(veins.map { it.point }))
                .let { (topLeft, bottomRight) -> topLeft.w() to bottomRight.e() }
        val base = ".".repeat(boundary.getWidth())
        val state: Ground = (1..boundary.getHeight()).map { y ->
            Layer(veins.filter { it.atRow(y - 1) }.toSet(), base)
        }
        flow(state, boundary, source)
        return state
    }

    private fun flow(state: Ground, boundary: Boundary, start: Point) {
        val toProcess = ArrayDeque<Point>().apply { add(start) }
        while (toProcess.isNotEmpty()) {
            val now = toProcess.removeFirst()
            val south = now.s()
            if (!boundary.contains(south)
                    || isFlowing(state, boundary, toProcess, south) { it }) continue
            val veins = state[south.y].tokens().map { it.point }
            val stillWater = state[south.y].additions
                    .filter { it is StillWater }
                    .map { it.point }
            if (veins.any { it == south } || stillWater.any { it == south }) {
                val flowEast = isFlowing(state, boundary, toProcess, now.e()) { it }
                val flowWest = isFlowing(state, boundary, toProcess, now.w()) { it }
                if (flowEast || flowWest) continue
            }
            if (hasSideWalls(state, boundary, now)) {
                fillHorizontal(state, now)
            }
        }
    }

    private fun isFlowing(state: Ground,
                          boundary: Boundary,
                          toProcess: Deque<Point>,
                          point: Point,
                          toPrevious: (Point) -> Point): Boolean {
        return if (boundary.contains(point) && state.nothingAt(point)) {
            state.add(FlowingWater(point))
            toProcess.addFirst(toPrevious(point))
            toProcess.addFirst(point)
            true
        } else false
    }

    private fun hasSideWalls(state: Ground,
                             boundary: Boundary,
                             point: Point): Boolean =
            hasSideWall(state, boundary, point) { it.e() }
                    && hasSideWall(state, boundary, point) { it.w() }

    private fun hasSideWall(state: Ground,
                            boundary: Boundary,
                            point: Point,
                            toNext: (Point) -> Point): Boolean {
        var now = point
        while (boundary.contains(now)) {
            with(state[now.y]) {
                when (now) {
                    in tokens().map { it.point } -> return true
                    in additions.map { it.point } -> now = toNext(now)
                    else -> return false
                }
            }
        }
        return false
    }

    private fun fillHorizontal(state: Ground, point: Point) {
        fill(state, point) { it.e() }
        fill(state, point) { it.w() }
    }

    private fun fill(state: Ground, point: Point, toNext: (Point) -> Point) {
        var now = point
        with(state[now.y]) {
            while (tokens().none { it.point == now }) {
                additions.add(StillWater(now))
                now = toNext(now)
            }
        }
    }
}

private typealias Layer = MutableInputLine<Clay, Water>

private fun Layer.nothingAt(point: Point) =
        tokens().none { it.point == point } && additions.none { it.point == point }

private typealias Ground = List<Layer>

private fun Ground.nothingAt(point: Point) = this[point.y].nothingAt(point)

private fun <A : InputToken> Ground.add(addition: A) =
        this[addition.y()].additions.add(addition)

private data class Clay(override var point: Point) : InputToken(point) {
    override fun isActive() = true
    override fun toString() = "#"
}

private typealias Water = InputToken

private data class StillWater(override var point: Point) : Water(point) {
    override fun isActive() = true
    override fun toString() = "~"
}

private data class FlowingWater(override var point: Point) : Water(point) {
    override fun isActive() = true
    override fun toString() = "|"
}