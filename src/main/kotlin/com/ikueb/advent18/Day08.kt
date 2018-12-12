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
        return metadata.flatten().sum()
    }

    fun getValueOfRootNode(input: String): Int {
        val metadataCounts: Tree = mutableListOf()
        val nodeCounts: Tree = mutableListOf()
        val childNodes = mutableListOf<MutableList<Int>>()
        lateinit var currentChildNode: Tree
        var isHeader = true
        var isChildless = false
        val scanner = Scanner(input)
        while (scanner.hasNextInt()) {
            if (isHeader) {
                nodeCounts.add(scanner.nextInt())
                metadataCounts.add(scanner.nextInt())
                currentChildNode = mutableListOf()
                isHeader = nodeCounts.last() != 0
                if (isHeader) {
                    childNodes.add(currentChildNode)
                }
                isChildless = !isHeader
                continue
            }
            val metadata = (1..metadataCounts.last())
                    .map { scanner.nextInt() }
            metadataCounts.removeLast()
            val childResult = metadata.sumBy {
                if (isChildless) it
                else currentChildNode.getOrZero(it - 1)
            }
            currentChildNode = if (metadataCounts.isEmpty()) {
                mutableListOf()
            } else {
                if (!isChildless) {
                    childNodes.removeLast()
                }
                childNodes.last()
            }
            currentChildNode.add(childResult)
            isHeader = nodeCounts.decrementAndGetLast() >= 1
            isChildless = false
        }
        return currentChildNode.sum()
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

private fun Tree.getOrZero(n: Int) = if (n < size) this[n] else 0

private fun <T> MutableList<T>.removeLast() = removeAt(lastIndex)