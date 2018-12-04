package com.ikueb.advent18

object Day03 {

    fun getOverlappingRegions(input: List<String>): Int {
        return getPointCounts(input.map { parse(it) })
                .filter { it.value >= 2 }
                .count()
    }

    fun getNonOverlappingId(input: List<String>): Int {
        val claims = input.map { parse(it) }
        val overlaps = getPointCounts(claims)
                .filter { it.value >= 2 }
        return claims.find { claim ->
            overlaps.keys.intersect(claim.getPoints()).isEmpty()
        }!!.id
    }

    private fun getPointCounts(input: List<Claim>): Map<Point, Int> = input
            .flatMap { it.getPoints() }
            .groupingBy { it }
            .eachCount()
}

private val REGEX = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".toRegex()

private fun parse(input: String) = REGEX.find(input)!!.destructured
        .let { (id, x, y, xSize, ySize) ->
            Claim(id.toInt(), x.toInt(), y.toInt(), xSize.toInt(), ySize.toInt())
        }

data class Claim(val id: Int, val x: Int, val y: Int, val xSize: Int, val ySize: Int) {
    private fun getXRange() = x..(x + xSize - 1)

    private fun getYRange() = y..(y + ySize - 1)

    fun getPoints(): Set<Point> = getXRange()
            .flatMap { x -> getYRange().map { y -> Point(x, y) } }
            .toSet()
}

data class Point(val x: Int, val y: Int)