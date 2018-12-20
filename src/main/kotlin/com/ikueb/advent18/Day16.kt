package com.ikueb.advent18

object Day16 {

    fun likelyOpscodes(input: List<String>): Int {
        var result = 0
        process(input) { if (countCandidates(it)) result++ }
        return result
    }

    fun process(input: List<String>, postProcessor: (Pair<Int, List<Opcode>>) -> Unit) {
        var isPreviousEmpty = false
        val current = mutableListOf<String>()
        for (line in input) {
            if (line.isEmpty()) {
                if (isPreviousEmpty && current.isEmpty()) break
                postProcessor.invoke(processSample(current.toList()))
                current.clear()
                isPreviousEmpty = true
            } else {
                current.add(line)
            }
        }
        postProcessor.invoke(processSample(current.toList()))
    }

    private fun countCandidates(result: Pair<Int, List<Opcode>>) = result.second.size >= 3

    private fun processSample(sample: List<String>): Pair<Int, List<Opcode>> {
        if (sample.size != 3) return -1 to emptyList()
        val before = "Before: \\[(\\d), (\\d), (\\d), (\\d)\\]".parseFor(
                sample[0]) { (a, b, c, d) ->
            listOf(a.toInt(), b.toInt(), c.toInt(), d.toInt())
        }
        val (n, a, b, c) = "(\\d+) (\\d) (\\d) (\\d)".parseFor(
                sample[1]) { (n, a, b, c) ->
            listOf(n.toInt(), a.toInt(), b.toInt(), c.toInt())
        }
        val after = "After:  \\[(\\d), (\\d), (\\d), (\\d)\\]".parseFor(
                sample[2]) { (a, b, c, d) ->
            listOf(a.toInt(), b.toInt(), c.toInt(), d.toInt())
        }
        return n to opcodes.filter { it.invoke(before, a, b) == after[c] }
    }
}

private typealias RegisterState = List<Int>

private typealias Opcode = (RegisterState, Int, Int) -> Int

private val addr: Opcode = { state, a, b -> state[a] + state[b] }
private val addi: Opcode = { state, a, b -> state[a] + b }
private val mulr: Opcode = { state, a, b -> state[a] * state[b] }
private val muli: Opcode = { state, a, b -> state[a] * b }
private val banr: Opcode = { state, a, b -> state[a] and state[b] }
private val bani: Opcode = { state, a, b -> state[a] and b }
private val borr: Opcode = { state, a, b -> state[a] or state[b] }
private val bori: Opcode = { state, a, b -> state[a] or b }
private val setr: Opcode = { state, a, _ -> state[a] }
private val seti: Opcode = { _, a, _ -> a }
private val gtir: Opcode = { state, a, b -> if (a > state[b]) 1 else 0 }
private val gtri: Opcode = { state, a, b -> if (state[a] > b) 1 else 0 }
private val gtrr: Opcode = { state, a, b -> if (state[a] > state[b]) 1 else 0 }
private val eqir: Opcode = { state, a, b -> if (a == state[b]) 1 else 0 }
private val eqri: Opcode = { state, a, b -> if (state[a] == b) 1 else 0 }
private val eqrr: Opcode = { state, a, b -> if (state[a] == state[b]) 1 else 0 }

private val opcodes = setOf(
        addr, addi, mulr, muli, banr, bani, borr, bori,
        setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)
