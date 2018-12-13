package com.ikueb.advent18

object Day09 {

    fun getHighestScore(input: String) =
            "(\\d+) players; last marble is worth (\\d+) points"
                    .parseWith(input) { (players, lastPoint) ->
                        play(players.toInt(), lastPoint.toInt())
                    }

    private fun play(players: Int, lastPoint: Int): Int {
        val ring: Ring = mutableListOf(0)
        val scores = mutableMapOf<Int, Int>()
        var currentPlayer = 1
        var currentIndex = 0
        for (i in 1..lastPoint) {
            val (nextIndex, score) = ring.addMarble(currentIndex, i)
            currentIndex = nextIndex
            if (score != null) {
                scores.merge(currentPlayer, score, Integer::sum)
            }
            currentPlayer = (currentPlayer + 1).let { if (it > players) 1 else it }
        }
        return scores.values.maxBy { it }!!
    }
}

private typealias Ring = MutableList<Int>

private fun Ring.addMarble(currentIndex: Int, i: Int): Pair<Int, Int?> {
    return if (i % 23 == 0) {
        (currentIndex - 7)
                .let { if (it < 0) it + size else it }
                .let { it to i + removeAt(it) }
    } else {
        (currentIndex + 2)
                .let { if (it >= size + 1) it - size else it }
                .let { add(it, i); it to null }
    }
}