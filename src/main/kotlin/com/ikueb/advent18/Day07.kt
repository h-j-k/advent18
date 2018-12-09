package com.ikueb.advent18

object Day07 {

    private const val DEFINITION = "Step (.) must be finished before step (.) can begin."

    fun getOrderedSteps(input: List<String>): String {
        val steps = input.parseWith(DEFINITION) { (dependsOn, step) ->
            Step(step = step, dependsOn = dependsOn)
        }
        val dependents = mutableMapOf<String, MutableList<String>>()
        val dependencies = mutableMapOf<String, MutableList<String>>()
        steps.forEach {
            dependents.mergeMutableListValues(it.dependsOn, it.step)
            dependencies.mergeMutableListValues(it.step, it.dependsOn)
        }
        val result = mutableListOf<String>()
        val start = dependents
                .filter { (key, _) -> !dependents.flatMap { it.value }.contains(key) }
        val toProcess = start.keys.sorted().toMutableSet()
        while (toProcess.isNotEmpty()) {
            val n = toProcess.sortAndPop()
            result.add(n)
            (dependents[n] ?: mutableListOf()).forEach { dependent ->
                dependencies.computeIfPresent(dependent) { _, value ->
                    value.remove(n)
                    if (value.isEmpty()) null else value
                }.let { if (it == null) toProcess.add(dependent) }
            }

        }
        return result.joinToString("")
    }
}

private fun <T : Comparable<T>> MutableSet<T>.sortAndPop() =
        sortedBy { it }[0].also { remove(it) }

private data class Step(val step: String, val dependsOn: String)