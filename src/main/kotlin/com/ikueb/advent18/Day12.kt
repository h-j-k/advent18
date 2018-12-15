package com.ikueb.advent18

object Day12 {

    fun getSumOfPlantedPots(input: List<String>) =
            process(input, (1..20).asSequence(), 0).toInt()

    fun getSumOfPlantedPotsAt(input: List<String>, target: Long) =
            process(input = input, target = target)

    private fun process(input: List<String>,
                        sequence: Sequence<Int> = generateSequence(1) { it + 1 },
                        target: Long): Long {
        val state = input[0].replace("initial state: ", "")
                .mapIndexed { i, c -> i to (c == '#') }.toMap(mutableMapOf())
        val rules = input.drop(2).map { PlantPredicate(it) }
        var previous = setOf<Int>()
        var last = 0
        for (i in sequence) {
            val stateCopy = state.toMap()
            val start = stateCopy.keys.minBy { it }!! - 3
            val end = stateCopy.keys.maxBy { it }!! + 3
            val current = (start..end).associateWith { n ->
                rules.map { it.isPlantNext(n, stateCopy) }.firstOrNull()
            }.mapValues { it.value ?: false }
                    .apply { state.putAll(this) }
                    .filterValues { it }.keys
            if (current.map { it - 1 }.toSet() == previous) {
                last = i
                break
            }
            previous = current
        }
        return state.filterValues { it }.keys
                .map { it + (target - last) }
                .reduce(Long::plus)
    }
}

data class PlantPredicate(val plants: List<Boolean>, val outcome: Boolean) {

    constructor(input: String) :
            this(input.substring(0, 5).map { it == '#' }, input.endsWith("#"))

    fun isPlantNext(n: Int, stateCopy: Map<Int, Boolean>) =
            if ((-2..2).map { n + it }.all {
                        (stateCopy[it] == true) == plants[it - n + 2]
                    }) outcome else null
}

private fun <T> List<T?>.firstOrNull(): T? {
    forEach { if (it != null) return it }
    return null
}