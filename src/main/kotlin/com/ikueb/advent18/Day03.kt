package com.ikueb.advent18

private typealias Result = Pair<List<Claim>, Map<Point, Set<Claim>>>

object Day03 {

    private const val PATTERN = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)"

    fun getOverlappingRegions(input: List<String>) =
            parseAndFindOverlappingPoints(input).second.count()

    fun getNonOverlappingId(input: List<String>): Int {
        val (claims, overlaps) = parseAndFindOverlappingPoints(input)
        val overlappingIds = overlaps.flatMapTo(mutableSetOf()) { it.value }
        return claims.find { it -> !overlappingIds.contains(it) }!!.id
    }

    private fun parseAndFindOverlappingPoints(input: List<String>): Result {
        val claims = input.parseWith(PATTERN) { (id, x, y, xSize, ySize) ->
            Claim(id.toInt(), x.toInt(), y.toInt(), xSize.toInt(), ySize.toInt())
        }
        return claims to getPointCounts(claims).filter { it.value.size >= 2 }
    }

    private fun getPointCounts(input: List<Claim>): Map<Point, Set<Claim>> =
            input.map { claim -> claim.getPoints().associateTo(mutableMapOf()) { it to setOf(claim) } }
                    .reduce { a, b -> a.collate(b) { x, y -> x.plus(y) } }
}

private fun <K, V> MutableMap<K, V>.collate(other: Map<K, V>, reducer: (V, V) -> V): MutableMap<K, V> =
        this.apply { other.forEach { merge(it.key, it.value, reducer) } }

private data class Claim(val id: Int, val x: Int, val y: Int, val xSize: Int, val ySize: Int) {
    private fun getXRange() = x..(x + xSize - 1)

    private fun getYRange() = y..(y + ySize - 1)

    fun getPoints(): Set<Point> = getXRange()
            .flatMap { x -> getYRange().map { y -> Point(x, y) } }
            .toSet()
}

private data class Point(val x: Int, val y: Int)