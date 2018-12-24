package com.ikueb.advent18

import com.ikueb.advent18.model.Instruction
import com.ikueb.advent18.model.InstructionPointer
import com.ikueb.advent18.model.RegisterState

object Day19 {

    fun getValue(input: List<String>,
                 registerState: RegisterState = mutableListOf(0, 0, 0, 0, 0, 0)): Int {
        val isPartTwo = registerState[0] == 1
        val pointer = InstructionPointer(
                "#ip (\\d+)".parseFor(input[0]) { (i) -> i.toInt() })
        val instructions = input.drop(1).map {
            "([^ ]+) (\\d+) (\\d+) (\\d+)".parseFor(it) { (opcode, a, b, c) ->
                Instruction(opcode, a.toInt(), b.toInt(), c.toInt())
            }
        }
        var next = registerState[pointer.binding]
        var target = 0
        while (next in 0..(instructions.size - 1)) {
            registerState[pointer.binding] = next
            instructions[next].invoke(registerState)
            if (isPartTwo && registerState.drop(1).all { it > 0 }) {
                target = registerState.max()!!
                break
            }
            next = pointer.update(registerState)
        }
        if (isPartTwo) {
            registerState[0] = (1..target)
                    .mapNotNull { if (target % it == 0) it else null }
                    .sum()
        }
        return registerState[0]
    }
}