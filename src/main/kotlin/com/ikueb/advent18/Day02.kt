package com.ikueb.advent18

object Day02 {
    fun getChecksum(input: List<String>) = input
            .map { word -> word.groupingBy { it }.eachCount() }
            .map { Pair(it.containsValue(2).toInt(), it.containsValue(3).toInt()) }
            .reduce { a, b -> Pair(a.first + b.first, a.second + b.second) }
            .let { it.first * it.second }

    private fun Boolean.toInt() = if (this) 1 else 0

    fun getCommonLetters(input: List<String>): String {
        val sums = mutableMapOf<Int, Set<String>>()
        for (current in input) {
            val currentSum = current.sumBy { it.toInt() }
            val found = sums.filterKeys { Math.abs(currentSum - it) <= 25 }
                    .flatMap { it.value }
                    .map { getCandidateResult(current, it) }
                    .find { it != null }
            if (found != null) {
                return found
            } else {
                sums.mergeSetValues(currentSum, current)
            }
        }
        throw IllegalArgumentException("No results.")
    }

    private fun getCandidateResult(a: String, b: String): String? {
        var differenceIndex = -1
        var count = 1
        for (i in a.indices) {
            if (a[i] != b[i]) {
                differenceIndex = i
                count--
                if (count < 0) {
                    return null
                }
            }
        }
        return a.substring(0, differenceIndex) + a.substring(differenceIndex + 1)
    }
}