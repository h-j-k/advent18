package com.ikueb.advent18

fun resultingFrequency(input: List<String>): Int {
    return input.sumBy { it.toInt() }
}

fun repeatingFrequencyTwice(input: List<String>): Int {
    val copy = input.map { it.toInt() }
    val counter = mutableSetOf<Int>()
    var sum = 0
    var i = 0
    do {
        sum += copy[i]
        i = if (i == input.size - 1) 0 else i + 1
    } while (counter.add(sum))
    return sum
}