package com.ikueb.advent18

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

inline fun <T> String.parseFor(input: CharSequence,
                               mapper: (MatchResult.Destructured) -> T) =
        (toRegex().matchEntire(input)
                ?: throw IllegalArgumentException("Wrong format."))
                .destructured.let(mapper)

inline fun <T> List<CharSequence>.parseWith(pattern: String,
                                            mapper: (MatchResult.Destructured) -> T) =
        map { pattern.parseFor(it, mapper) }

fun <K, V> MutableMap<K, Set<V>>.mergeSetValues(key: K, value: V) =
        merge(key, setOf(value)) { a, b -> a.plus(b) }

fun <K, V> MutableMap<K, MutableList<V>>.mergeMutableListValues(key: K, value: V) =
        merge(key, mutableListOf(value)) { a, b -> a.addAll(b); a }

fun <T, R> Iterable<T>.parallelMap(mapper: suspend (T) -> R) =
        runBlocking { map { async { mapper(it) } }.map { it.await() } }

fun <K, V> Map<K, V>.flip() = this.entries
        .groupBy { it.value }
        .mapValues { (_, list) -> list.map { it.key } }

fun <T> List<T?>.firstNonNull(): T? {
    forEach { if (it != null) return it }
    return null
}

fun <T> List<T>.lastIndexMatching(predicate: (T) -> Boolean) =
        lastIndexOf(last(predicate))