package com.ikueb.advent18

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

inline fun <T> String.parseWith(input: CharSequence, mapper: (MatchResult.Destructured) -> T) =
        toRegex().matchEntire(input)!!.destructured.let(mapper)

inline fun <T> List<CharSequence>.parseWith(pattern: String, mapper: (MatchResult.Destructured) -> T) =
        map { pattern.parseWith(it, mapper) }

fun <K, V> MutableMap<K, Set<V>>.mergeSetValues(key: K, value: V) =
        merge(key, setOf(value)) { a, b -> a.plus(b) }

fun <T, R> Iterable<T>.parallelMap(mapper: suspend (T) -> R) =
        runBlocking { map { async { mapper(it) } }.map { it.await() } }

data class Point(val x: Int, val y: Int) {
    constructor(x: String, y: String) : this(x.toInt(), y.toInt())
}