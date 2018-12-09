package com.ikueb.advent18

import com.ikueb.advent18.Day07.getOrderedSteps
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day07Test {

    @Test
    fun partOneExample() {
        assertThat(getOrderedSteps(example)).isEqualTo("CABDFE")
    }

    @Test
    fun partOne() {
        assertThat(getOrderedSteps(getInput("Day07"))).isEqualTo("OKBNLPHCSVWAIRDGUZEFMXYTJQ")
    }

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