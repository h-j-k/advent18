package com.ikueb.advent18

object Day14 {

    fun getTenRecipeScoresAfter(input: Int): String =
            process { it.length < input + 10 }
                    .substring(input, input + 10)

    fun getFirstRecipes(input: Int): Int =
            input.toString().let {
                process { recipes -> !recipes.endsWith(it) }.indexOf(it)
            }

    private fun process(predicate: (Recipes) -> Boolean): Recipes {
        var (elfOne, elfTwo) = Pair(0, 1)
        val state: Recipes = StringBuilder("37")
        while (predicate(state)) {
            val temp = state[elfOne].asNumber() + state[elfTwo].asNumber()
            if (temp > 9) state.append(temp / 10)
            if (!predicate(state)) break
            state.append(temp % 10)
            elfOne = state.getNext(elfOne)
            elfTwo = state.getNext(elfTwo)
        }
        return state
    }
}

private typealias Recipes = StringBuilder

private fun Recipes.getNext(index: Int) =
        (index + 1 + this[index].asNumber()) % length

private fun Recipes.endsWith(target: String) = length >= target.length
        && substring(length - target.length, length) == target

private fun Char.asNumber() = when (this) {
    in '0'..'9' -> toInt() - '0'.toInt()
    else -> throw NumberFormatException()
}