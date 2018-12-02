package com.ikueb.advent18

fun getChecksum(input: List<String>): Int {
    return input
            .map { split(it) }
            .reduce { a, b -> Pair(a.first + b.first, a.second + b.second) }
            .let { it.first * it.second }
}

private fun Boolean.toInt() = if (this) 1 else 0

private fun split(word: String): Pair<Int, Int> {
    val counts = word
            .groupingBy { it }
            .eachCount()
    return Pair(counts.containsValue(2).toInt(), counts.containsValue(3).toInt())
}