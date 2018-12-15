package com.ikueb.advent18

object Day12 {

    fun getSumOfPlantedPots(input: List<String>): Int {
        val state = input[0].replace("initial state: ", "")
                .mapIndexed { i, c -> i to (c == '#') }.toMap(mutableMapOf())
        val rules = input.drop(2).map { PlantPredicate(it) }
        for (i in 1..20) {
            val stateCopy = state.toMap()
            val start = stateCopy.keys.minBy { it }!! - 3
            val end = stateCopy.keys.maxBy { it }!! + 3
            (start..end).associateWith { n ->
                rules.map { it.isPlantNext(n, stateCopy) }.firstOrNull()
            }.forEach { state[it.key] = it.value ?: false }
        }
        return state.filterValues { it }.keys.sum()
    }
}

data class PlantPredicate(val plants: List<Boolean>,
                          val outcome: Boolean) {

    constructor(input: String) : this(
            input.substring(0, 5).map { it == '#' },
            input.endsWith("#"))

    fun isPlantNext(n: Int, stateCopy: Map<Int, Boolean>) =
            if ((-2..2).map { n + it }.all {
                        (stateCopy[it] == true) == plants[it - n + 2]
                    }) outcome else null
}

private fun <T> List<T?>.firstOrNull(): T? {
    forEach { if (it != null) return it }
    return null
}