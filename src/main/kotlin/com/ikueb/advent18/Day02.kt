package com.ikueb.advent18

object Day02 {
    fun getChecksum(input: List<String>) = input
            .map { split(it) }
            .reduce { a, b -> Pair(a.first + b.first, a.second + b.second) }
            .let { it.first * it.second }

    private fun split(word: String): Pair<Int, Int> {
        val counts = word
                .groupingBy { it }
                .eachCount()
        return Pair(counts.containsValue(2).toInt(), counts.containsValue(3).toInt())
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    fun getCommonLetters(input: List<String>): String {
        val sums = mutableMapOf<Int, List<String>>()
        for (current in input) {
            val currentSum = current.sumBy { it.toInt() }
            val found = sums.filter { Math.abs(currentSum - it.key) <= 25 }
                    .flatMap { it.value }
                    .map { getCandidateResult(current, it) }
                    .find { it != null }
            if (found != null) {
                return found
            } else {
                sums.merge(currentSum, listOf(current)) { a, b -> a.plus(b) }
            }
        }
        throw IllegalStateException("No candidates found")
    }

    private fun getCandidateResult(a: String, b: String): String? {
        var differenceIndex = -1
        var count = 1
        for (i in a.indices) {
            if (a[i] != b[i]) {
                differenceIndex = i
                count--
                if (count < 0) return null
            }
        }
        return a.substring(0, differenceIndex) + a.substring(differenceIndex + 1)
    }
}