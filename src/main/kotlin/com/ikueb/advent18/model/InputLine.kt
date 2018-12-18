package com.ikueb.advent18.model

data class InputLine<T : InputToken>(val tokens: Set<T>, val base: String) {

    fun at(index: Int) = base[index]
}