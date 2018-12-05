package com.ikueb.advent18

object Day05 {

    fun getResultingLength(input: String) = process(input).trim().length

    private fun process(input: String): String {
        if (input.length < 2) {
            return input
        } else if (input.length == 2) {
            return if (input[0].isOppositeAscii(input[1])) "" else input
        }
        var copy = input
        var i = 1
        while (i < copy.length - 2) {
            val (isModified, replacement) = processTriplet(copy.substring(i - 1, i + 2))
            if (isModified) {
                copy = copy.substring(0, i - 1) + replacement + copy.substring(i + 2)
                if (i > 1) {
                    i--
                }
            } else {
                i++
            }
        }
        return copy
    }

    private fun processTriplet(triplet: String): Pair<Boolean, String> {
        if (triplet.length != 3) {
            throw IllegalArgumentException("Expecting three-letter input.")
        }
        val (x, y, z) = triplet.toCharArray()
        return if (x.isOppositeAscii(y)) {
            if (y.isOppositeAscii(z)) true to "" else true to z.toString()
        } else {
            if (y.isOppositeAscii(z)) true to x.toString() else false to triplet
        }
    }

    private fun Char.isOppositeAscii(other: Char) = Math.abs(toInt() - other.toInt()) == 32
}