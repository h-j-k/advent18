package com.ikueb.advent18

object Day16 {

    fun likelyOpcodes(input: List<String>): Int {
        var result = 0
        process(input) { if (it.second.size >= 3) result++ }
        return result
    }

    fun solveProgram(input: List<String>): Int {
        val mapped = mutableMapOf<Int, String>()
        with(opcodes.keys.associateWith { mutableMapOf<Int, Int>() }) {
            process(input) {
                it.second.forEach { candidate ->
                    this[candidate]!!.merge(it.first, 1, Integer::sum)
                }
            }
            while (any { it.value.isNotEmpty() }) {
                entries.filter { it.value.isNotEmpty() }
                        .sortedWith(compareBy { it.value.size })
                        .firstOrNull()?.let { current ->
                            val opcode = current.value.maxBy { it.value }!!.key
                            mapped[opcode] = current.key
                            values.forEach { ranked -> ranked.remove(opcode) }
                        }
            }
        }
        val registerState = mutableListOf(0, 0, 0, 0)
        for (line in findProgram(input)) {
            val (n, a, b, c) = "(\\d+) (\\d) (\\d) (\\d)".parseFor(
                    line) { (n, a, b, c) ->
                listOf(n.toInt(), a.toInt(), b.toInt(), c.toInt())
            }
            registerState[c] = opcodes[mapped[n]]!!.invoke(registerState, a, b)
        }
        return registerState[0]
    }

    private fun findProgram(input: List<String>) =
            input.lastIndexMatching { it.isEmpty() }
                    .let { found ->
                        input.subList(found + 1, input.size).filter { it.isNotEmpty() }
                    }

    private fun process(input: List<String>,
                        postProcessor: (Pair<Int, Set<String>>) -> Unit) {
        input.subList(0, input.lastIndexMatching { it.isEmpty() })
                .chunked(4) { postProcessor.invoke(processSample(it)) }
    }

    private fun processSample(sample: List<String>): Pair<Int, Set<String>> {
        if (sample.size != 4) return -1 to emptySet()
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
        return n to opcodes.filterValues {
            it.invoke(before, a, b) == after[c]
        }.keys
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

private val opcodes = mapOf(
        "addr" to addr, "addi" to addi, "mulr" to mulr, "muli" to muli,
        "banr" to banr, "bani" to bani, "borr" to borr, "bori" to bori,
        "setr" to setr, "seti" to seti, "gtir" to gtir, "gtri" to gtri,
        "gtrr" to gtrr, "eqir" to eqir, "eqri" to eqri, "eqrr" to eqrr)
