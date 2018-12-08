package com.ikueb.advent18

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates.notNull

object Day04 {

    private val PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun strategyOne(input: List<String>) = (mapGuardsToNaps(input)
            .maxBy { it.value.sumBy { nap -> nap.last - nap.first + 1 } }
            ?: throw IllegalArgumentException("No results."))
            .let { it.key * mostCommon(it.value).key }

    fun strategyTwo(input: List<String>) = (mapGuardsToNaps(input)
            .mapValues { mostCommon(it.value) }
            .maxBy { it.value.value }
            ?: throw IllegalArgumentException("No results."))
            .let { it.key * it.value.key }

    private fun mapGuardsToNaps(input: List<String>): Map<Int, Set<IntRange>> {
        val results = mutableMapOf<Int, Set<IntRange>>()
        var guard: Int by notNull()
        lateinit var asleepAt: Log
        input.parseWith("\\[(.*)\\] (.*)") { (timestamp, entry) ->
            Log(LocalDateTime.parse(timestamp, PARSER), entry)
        }.sorted().forEach { log ->
            when {
                log.guardEntry != null -> guard = log.guardEntry
                log.isAsleepEntry -> asleepAt = log
                log.isAwakeEntry -> results.mergeSetValues(guard, asleepAt..log)
            }
        }
        return results
    }

    private fun mostCommon(set: Set<IntRange>) =
            set.flatten().groupingBy { it }.eachCount().maxBy { it.value }
                    ?: throw IllegalArgumentException("No results.")
}

private data class Log(val timestamp: LocalDateTime, val entry: String) : Comparable<Log> {
    val guardEntry = "Guard #(\\d+) begins shift".toRegex()
            .matchEntire(entry)?.let { it.groupValues[1].toInt() }
    val isAwakeEntry = entry == "wakes up"
    val isAsleepEntry = entry == "falls asleep"
    val minute = timestamp.minute

    override fun compareTo(other: Log) = timestamp.compareTo(other.timestamp)

    operator fun rangeTo(other: Log): IntRange = minute..(other.minute - 1)
}