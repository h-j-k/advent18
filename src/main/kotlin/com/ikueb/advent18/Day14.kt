package com.ikueb.advent18

object Day14 {

    fun getTenRecipeScoresAfter(input: Int): String {
        var (elfOne, elfTwo) = listOf(0, 1)
        val state: Recipes = StringBuilder("37")
        with(state) {
            while (length < input + 10) {
                generateAndAppend(elfOne, elfTwo)
                elfOne = getNext(elfOne)
                elfTwo = getNext(elfTwo)
            }
        }
        return state.substring(input, input + 10)
    }
}

private typealias Recipes = StringBuilder

private fun Recipes.generateAndAppend(elfOne: Int, elfTwo: Int) =
        append(this[elfOne].asNumber() + this[elfTwo].asNumber())

private fun Recipes.getNext(index: Int) =
        (index + 1 + this[index].asNumber()) % length

private fun Char.asNumber() = when (this) {
    in '0'..'9' -> toInt() - '0'.toInt()
    else -> throw NumberFormatException()
}