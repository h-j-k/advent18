package com.ikueb.advent18

object Day07 {

    private const val DEFINITION = "Step (.) must be finished before step (.) can begin."

    fun getOrderedSteps(input: List<String>,
                        total: Int = 1,
                        effort: (String) -> Int = { 1 }): Pair<String, ElfWorkers> {
        val steps = input.parseWith(DEFINITION) { (dependsOn, step) ->
            Step(step = step, dependsOn = dependsOn)
        }
        val dependents: Tracker = mutableMapOf()
        val dependencies: Tracker = mutableMapOf()
        steps.forEach {
            dependents.mergeMutableListValues(it.dependsOn, it.step)
            dependencies.mergeMutableListValues(it.step, it.dependsOn)
        }
        val target = steps.flatMap { listOf(it.step, it.dependsOn) }.distinct().count()
        val toProcess = dependents
                .filter { (key, _) -> !dependents.flatMap { it.value }.contains(key) }.keys
                .sorted()
                .toMutableSet()
        val workers = ElfWorkers(total, effort)
        with(mutableListOf<String>()) {
            while (size < target) {
                workers.inWith(toProcess)
                addAll(workers.outAs(dependents, dependencies, toProcess))
            }
            return joinToString("") to workers
        }
    }
}

private fun <T : Comparable<T>> MutableSet<T>.sortAndPop() =
        sortedBy { it }[0].also { remove(it) }

private data class Step(val step: String, val dependsOn: String)

private typealias Tracker = MutableMap<String, MutableList<String>>

data class ElfWorkers(val total: Int, val effort: (String) -> Int) {

    private val workload = mutableMapOf<String, Int>()
    private var counter = 0

    fun getTimeTaken() = counter

    fun inWith(toProcess: MutableSet<String>) {
        while (total - workload.size > 0 && toProcess.isNotEmpty()) {
            val n = toProcess.sortAndPop()
            workload[n] = effort(n)
        }
    }

    fun outAs(dependents: Tracker,
              dependencies: Tracker,
              toProcess: MutableSet<String>): List<String> {
        val done = mutableListOf<String>()
        workload.keys.toSet().forEach { key ->
            workload.updateAndIfNull(key, { _, value ->
                if (value == 1) null else value - 1
            }, {
                (dependents[key] ?: mutableListOf()).forEach { dependent ->
                    dependencies.updateAndIfNull(dependent, { _, value ->
                        value.remove(key)
                        if (value.isEmpty()) null else value
                    }, { toProcess.add(dependent) })
                }
                done.add(key)
            })
        }
        counter++
        return done.toList()
    }
}

private fun <K, V> MutableMap<K, V>.updateAndIfNull(key: K,
                                                    remapping: (K, V) -> V?,
                                                    processor: () -> Unit) {
    computeIfPresent(key, remapping).let { if (it == null) processor() }
}