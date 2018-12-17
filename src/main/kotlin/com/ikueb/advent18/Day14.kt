package com.ikueb.advent18

object Day14 {

    fun getTenRecipeScoresAfter(input: Int): String =
            process { it.length < input + 10 }
                    .substring(input, input + 10)

    fun getFirstRecipes(input: Int): Int =
            input.toString().run {
                process { !it.endsWith(this) }.indexOf(this)
            }

    private fun process(predicate: (Recipes) -> Boolean): Recipes {
        var (elfOne, elfTwo) = Pair(0, 1)
        val state: Recipes = StringBuilder("37")
        state.ensureCapacity(30000000)
        while (predicate(state)) {
            with(state) {
                generateAndAppend(elfOne, elfTwo)
                elfOne = getNext(elfOne)
                elfTwo = getNext(elfTwo)
            }
        }
        return state
    }
}

private typealias Recipes = StringBuilder

private fun Recipes.generateAndAppend(elfOne: Int, elfTwo: Int) =
        append(this[elfOne].asNumber() + this[elfTwo].asNumber())

private fun Recipes.getNext(index: Int) =
        (index + 1 + this[index].asNumber()) % length

private fun Recipes.endsWith(target: String) = length >= target.length
        && substring(length - target.length, length) == target

private fun Char.asNumber() = when (this) {
    in '0'..'9' -> toInt() - '0'.toInt()
    else -> throw NumberFormatException()
}