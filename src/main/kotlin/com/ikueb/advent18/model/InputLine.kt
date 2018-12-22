package com.ikueb.advent18.model

data class InputLine<T : InputToken>(val tokens: Set<T>, val base: String) : LineWithToken<T> {

    override fun at(index: Int) = base[index]

    override fun tokens() = tokens

    override fun base() = base

    override fun render(offset: Point) = buildString {
        append(base)
        tokens.forEach { setCharAt(it.x() - offset.x, it.toString()[0]) }
    }
}