package com.ikueb.advent18

import com.ikueb.advent18.model.Opcode
import com.ikueb.advent18.model.RegisterState
import com.ikueb.advent18.model.opcodes

object Day19 {

    fun getRegisterValue(input: List<String>): Int {
        val pointer = InstructionPointer(
                "#ip (\\d+)".parseFor(input[0]) { (i) -> i.toInt() })
        val instructions = input.drop(1).map {
            "([^ ]+) (\\d+) (\\d+) (\\d+)".parseFor(it) { (opcode, a, b, c) ->
                Instruction(opcodes[opcode]!!, a.toInt(), b.toInt(), c.toInt())
            }
        }
        val registerState = mutableListOf(0, 0, 0, 0, 0, 0)
        var next = registerState[pointer.binding]
        while (next in 0..(instructions.size - 1)) {
            registerState[pointer.binding] = next
            instructions[next].invoke(registerState)
            next = pointer.update(registerState[pointer.binding])
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