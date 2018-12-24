package com.ikueb.advent18

import com.ikueb.advent18.model.Instruction
import com.ikueb.advent18.model.InstructionPointer
import com.ikueb.advent18.model.RegisterState

object Day21 {

    fun getHaltingValue(input: List<String>): Int {
        val pointer = InstructionPointer(
                "#ip (\\d+)".parseFor(input[0]) { (i) -> i.toInt() })
        val instructions = input.drop(1).map {
            "([^ ]+) (\\d+) (\\d+) (\\d+)".parseFor(it) { (opcode, a, b, c) ->
                Instruction(opcode, a.toInt(), b.toInt(), c.toInt())
            }
        }
        val (targetInstruction, targetRegister) = instructions.mapIndexed { i, it ->
            i to it.targetRegister()
        }.first { it.second != null }.let { (a, b) -> a to b!! }
        return sequence {
            val registerState: RegisterState = mutableListOf(0, 0, 0, 0, 0, 0)
            var next = registerState[pointer.binding]
            val seen = LinkedHashSet<Int>()
            while (next in 0..(instructions.size - 1)) {
                registerState[pointer.binding] = next
                instructions[next].invoke(registerState)
                next = pointer.update(registerState[pointer.binding])
                if (next == targetInstruction) {
                    if (!seen.add(registerState[targetRegister])) {
                        yield(seen.last())
                        return@sequence
                    } else yield(registerState[targetRegister])
                }
            }
        }.first()
    }
}

private fun Instruction.targetRegister() =
        if (opcode.endsWith("r") && a * b == 0) Math.abs(a - b) else null