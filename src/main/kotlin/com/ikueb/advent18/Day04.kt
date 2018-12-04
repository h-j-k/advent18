package com.ikueb.advent18

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Day04 {

    private val PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun strategyOne(input: List<String>): Int {
        return mapGuardsToNaps(input)
                .maxBy { it.value.sumBy { nap -> nap.last - nap.first + 1 } }!!
                .let { it.key * mostCommon(it.value).key }
    }

    fun strategyTwo(input: List<String>): Int {
        return mapGuardsToNaps(input)
                .mapValues { mostCommon(it.value) }
                .maxBy { it.value.value }!!
                .let { it.key * it.value.key }
    }

    private fun mapGuardsToNaps(input: List<String>): Map<Int, Set<IntRange>> {
        val results = mutableMapOf<Int, Set<IntRange>>()
        var guard = -1
        var asleepAt = -1
        input.parseWith("\\[(.*)\\] (.*)") { (timestamp, entry) ->
            Log(LocalDateTime.parse(timestamp, PARSER), entry)
        }.sorted().forEach { log ->
            when {
                log.guardEntry != null -> guard = log.guardEntry
                log.isAsleepEntry -> asleepAt = log.minute
                log.isAwakeEntry -> results.merge(
                        guard, setOf(asleepAt..(log.minute - 1))) { a, b -> a.plus(b) }
            }
        }
        return results
    }
}

private fun mostCommon(set: Set<IntRange>): Map.Entry<Int, Int> {
    return set.flatten().groupingBy { it }.eachCount().maxBy { it.value }!!
}

data class Log(val timestamp: LocalDateTime, val entry: String) : Comparable<Log> {
    val guardEntry = "Guard #(\\d+) begins shift".toRegex()
            .matchEntire(entry)?.let { it.groupValues[1].toInt() }
    val isAwakeEntry = entry == "wakes up"
    val isAsleepEntry = entry == "falls asleep"
    val minute = timestamp.minute

    override fun compareTo(other: Log) = timestamp.compareTo(other.timestamp)
}