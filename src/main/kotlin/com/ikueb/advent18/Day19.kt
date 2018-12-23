package com.ikueb.advent18

import com.ikueb.advent18.model.Opcode
import com.ikueb.advent18.model.RegisterState
import com.ikueb.advent18.model.opcodes

object Day19 {

    fun getValue(input: List<String>,
                 registerState: RegisterState = mutableListOf(0, 0, 0, 0, 0, 0)): Int {
        val isPartTwo = registerState[0] == 1
        val pointer = InstructionPointer(
                "#ip (\\d+)".parseFor(input[0]) { (i) -> i.toInt() })
        val instructions = input.drop(1).map {
            "([^ ]+) (\\d+) (\\d+) (\\d+)".parseFor(it) { (opcode, a, b, c) ->
                Instruction(opcodes[opcode]!!, a.toInt(), b.toInt(), c.toInt())
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
            next = pointer.update(registerState[pointer.binding])
        }
        if (isPartTwo) {
            registerState[0] = (1..target)
                    .mapNotNull { if (target % it == 0) it else null }
                    .sum()
        }
        return registerState[0]
    }
}

data class InstructionPointer(val binding: Int, var value: Int = 0) {
    fun update(newValue: Int): Int {
        value = newValue + 1
        return value
    }
}

data class Instruction(val opcode: Opcode, val a: Int, val b: Int, val c: Int) {
    fun invoke(state: RegisterState) {
        state[c] = opcode.invoke(state, a, b)
    }
}