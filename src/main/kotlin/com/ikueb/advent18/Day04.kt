package com.ikueb.advent18

import java.time.LocalDateTime

object Day04 {

    private const val PATTERN = "\\[1518-(..)-(..) (..):(..)\\] (.*)"

    fun strategyOne(input: List<String>): Int {
        val logs = input.parseWith(PATTERN) { (month, day, hour, minute, entry) ->
            Log(LocalDateTime.of(2018, month.toInt(), day.toInt(), hour.toInt(), minute.toInt()), entry)
        }.sorted()
        val guards = mutableMapOf<Int, Set<IntRange>>()
        var currentGuard = -1
        var asleepAt = -1
        for (log in logs) {
            if (log.guardEntry != null) {
                currentGuard = log.guardEntry
                continue
            }
            if (log.isAsleepEntry) {
                asleepAt = log.timestamp.minute
                continue
            }
            if (log.isAwakeEntry) {
                guards.merge(currentGuard, setOf(asleepAt..(log.timestamp.minute - 1))) { a, b -> a.plus(b) }
            }
        }
        return guards.maxBy { g -> g.value.sumBy { it.last - it.first + 1 } }!!
                .let { it.key * mostCommon(it.value) }

    }

    private fun mostCommon(set: Set<IntRange>): Int {
        set.flatten().groupingBy { it }.eachCount().forEach { println(it) }
        return set.flatten().groupingBy { it }.eachCount().maxBy { it.value }!!.key
    }

}

data class Log(val timestamp: LocalDateTime, val entry: String) : Comparable<Log> {
    val guardEntry = "Guard #(\\d+) begins shift".toRegex().matchEntire(entry)?.let { it.groupValues[1].toInt() }
    val isAwakeEntry = entry == "wakes up"
    val isAsleepEntry = entry == "falls asleep"

    override fun compareTo(other: Log) = timestamp.compareTo(other.timestamp)
}