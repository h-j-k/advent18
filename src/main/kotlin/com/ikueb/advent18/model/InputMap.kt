package com.ikueb.advent18.model

typealias InputMap<T> = List<InputLine<T>>

fun <T> InputMap<T>.getTokens() = flatMap { it.tokens }.toSet()

fun <T> InputMap<T>.at(point: Point) = this[point.y].at(point.x)

fun <T> List<String>.asInputMap(tokenProcessor: (Int, String) -> Set<T>,
                                baseProcessor: (String) -> String) =
        this.mapIndexed { i, line ->
            InputLine(tokenProcessor(i, line), baseProcessor(line))
        }