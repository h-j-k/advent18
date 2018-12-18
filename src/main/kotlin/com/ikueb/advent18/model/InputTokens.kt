package com.ikueb.advent18.model

typealias InputTokens<T> = Set<T>

fun <T : InputToken> InputTokens<T>.activeAndSorted() =
        filter { it.isActive() }.sortedBy { it.point }