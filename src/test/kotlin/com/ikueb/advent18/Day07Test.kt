package com.ikueb.advent18

import com.ikueb.advent18.Day07.getOrderedSteps
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val partOne: (Pair<String, ElfWorkers>) -> String = { it.first }
    private val partTwo: (Pair<String, ElfWorkers>) -> Int = { it.second.getTimeTaken() }

    @Test
    fun partOneExample() {
        assertThat(getOrderedSteps(example).let(partOne))
                .isEqualTo("CABDFE")
    }

    @Test
    fun partOne() {
        assertThat(getOrderedSteps(getInput("Day07")).let(partOne))
                .isEqualTo("OKBNLPHCSVWAIRDGUZEFMXYTJQ")
    }

    @Test
    fun partTwoExample() {
        assertThat(getOrderedSteps(example, 2) { it.toValue() }.let(partTwo))
                .isEqualTo(15)
    }

    @Test
    fun partTwo() {
        assertThat(getOrderedSteps(getInput("Day07"), 4) { it.toValue(60) }.let(partTwo))
                .isEqualTo(982)
    }

    private fun String.toValue(base: Int = 0) =
            base + 1 + this[0].toInt() - 'A'.toInt()

    private val example = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
    )
}