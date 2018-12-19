package com.ikueb.advent18.model

typealias InputTokens<T> = Set<T>

fun <T : InputToken> InputTokens<T>.active() = filter { it.isActive() }

fun <T : InputToken> InputTokens<T>.activeAndSorted() = active().sortedBy { it.point }