package com.ikueb.advent18.model

interface LineWithToken<T : InputToken> {

    fun tokens(): Set<T>

    fun base(): String

    fun at(index: Int): Char

    fun render(offset: Point = Point(0, 0)): String
}