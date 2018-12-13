package com.ikueb.advent18

import java.util.*
import kotlin.math.absoluteValue

object Day09 {

    fun getHighestScore(input: String) =
            "(\\d+) players; last marble is worth (\\d+) points"
                    .parseWith(input) { (players, lastPoint) ->
                        play(players.toInt(), lastPoint.toInt())
                    }

    private fun play(players: Int, lastPoint: Int): Long {
        val ring: Ring = ArrayDeque<Int>().apply { add(0) }
        val scores = mutableMapOf<Int, Long>()
        for (i in 1..lastPoint) {
            scores.merge(i % players, ring.addMarble(i), Long::plus)
        }
        return scores.values.maxBy { it }!!
    }
}

private typealias Ring = ArrayDeque<Int>

private fun Ring.addMarble(i: Int) =
        if (i % 23 == 0) {
            shift(-7)
            val result = removeFirst()
            shift()
            i + result.toLong()
        } else {
            shift()
            addFirst(i)
            0
        }

// https://todd.ginsberg.com/post/advent-of-code/2018/day9/
private fun Ring.shift(n: Int = 1) =
        if (n < 0) {
            repeat(n.absoluteValue) { addLast(removeFirst()) }
        } else {
            repeat(n) { addFirst(removeLast()) }
        }