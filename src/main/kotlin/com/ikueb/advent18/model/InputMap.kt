package com.ikueb.advent18.model

typealias InputMap<T> = List<LineWithToken<T>>

fun <T : InputToken> InputMap<T>.getTokens(): InputTokens<T> = flatMap { it.tokens() }.toSet()

fun <T : InputToken> InputMap<T>.at(point: Point) = this[point.y].at(point.x)

fun <T : InputToken> List<String>.asInputMap(tokenProcessor: (Int, String) -> Set<T>,
                                             baseProcessor: (String) -> String) =
        this.mapIndexed { i, line ->
            InputLine(tokenProcessor(i, line), baseProcessor(line))
        }