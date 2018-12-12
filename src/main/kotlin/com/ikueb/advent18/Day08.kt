package com.ikueb.advent18

import java.util.*

object Day08 {

    fun getSumOfMetadata(input: String): Int {
        val metadata = mutableListOf<List<Int>>()
        val metadataCounts: Tree = mutableListOf()
        val nodeCounts: Tree = mutableListOf()
        var isHeader = true
        val scanner = Scanner(input)
        while (scanner.hasNextInt()) {
            if (isHeader) {
                nodeCounts.add(scanner.nextInt())
                metadataCounts.add(scanner.nextInt())
                isHeader = nodeCounts.last() != 0
                continue
            }
            (1..metadataCounts.last())
                    .map { scanner.nextInt() }
                    .let { metadata.add(it) }
            metadataCounts.removeLast()
            isHeader = nodeCounts.decrementAndGetLast() >= 1
        }
        return metadata.flatten().sumBy { it }
    }
}

private typealias Tree = MutableList<Int>

private fun Tree.decrementAndGetLast(): Int {
    if (isEmpty()) return -1
    val result = last() - 1
    return if (result > 0) {
        set(lastIndex, result)
        result
    } else {
        removeAt(lastIndex)
        if (isEmpty()) -1 else (last() - 1).also { set(lastIndex, it) }
    }
}

private fun Tree.removeLast() = removeAt(lastIndex)