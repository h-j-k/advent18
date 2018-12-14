package com.ikueb.advent18

import java.util.*

object Day08 {

    fun getSumOfMetadata(input: String) = process(input).first

    fun getValueOfRootNode(input: String) = process(input).second

    private fun process(input: String): Pair<Int, Int> {
        val metadata: Tree = mutableListOf()
        val metadataCounts: Tree = mutableListOf()
        val nodeCounts: Tree = mutableListOf()
        val childNodes = mutableListOf<MutableList<Int>>()
                .apply { add(mutableListOf()) }
        lateinit var currentNode: Tree
        var isHeader = true
        val scanner = Scanner(input)
        while (scanner.hasNextInt()) {
            if (isHeader) {
                nodeCounts.add(scanner.nextInt())
                metadataCounts.add(scanner.nextInt())
                currentNode = mutableListOf()
                isHeader = (nodeCounts.last() != 0)
                        .apply { if (this) childNodes.add(currentNode) }
                continue
            }
            if (currentNode.isNotEmpty()) childNodes.removeLast()
            val entries = (1..metadataCounts.removeLast())
                    .map { scanner.nextInt() }
            metadata.add(entries.sum())
            currentNode = entries
                    .sumBy {
                        if (currentNode.isEmpty()) it
                        else currentNode.getOrZero(it - 1)
                    }.let {
                        with(childNodes.last()) { add(it); this }
                    }
            isHeader = nodeCounts.decrementAndGetLast() >= 1
        }
        return metadata.sum() to currentNode.sum()
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
        if (isEmpty()) -1 else (last() - 1).apply { set(lastIndex, this) }
    }
}

private fun Tree.getOrZero(n: Int) = if (n < size) this[n] else 0

private fun <T> MutableList<T>.removeLast() = removeAt(lastIndex)