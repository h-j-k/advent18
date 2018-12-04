package com.ikueb.advent18

inline fun <T> String.parseWith(input: CharSequence, mapper: (MatchResult.Destructured) -> T): T =
        toRegex().matchEntire(input)!!.destructured.let(mapper)

inline fun <T> List<CharSequence>.parseWith(pattern: String, mapper: (MatchResult.Destructured) -> T): List<T> =
        map { pattern.parseWith(it, mapper) }
