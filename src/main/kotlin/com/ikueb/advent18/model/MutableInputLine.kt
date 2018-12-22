package com.ikueb.advent18.model

data class MutableInputLine<T : InputToken, A : InputToken>(
        val inputLine: InputLine<T>,
        val additions: MutableSet<A> = mutableSetOf()) : LineWithToken<T> {

    constructor(tokens: Set<T>, base: String) : this(InputLine(tokens, base))

    override fun tokens() = inputLine.tokens

    override fun base() = inputLine.base

    override fun at(index: Int) = additions.firstOrNull { it.atColumn(index) }
            ?.let { it.toString()[0] } ?: inputLine.at(index)

    override fun render(offset: Point) = buildString {
        append(inputLine.render(offset))
        additions.forEach { setCharAt(it.x() - offset.x, it.toString()[0]) }
    }
}