package com.ikueb.advent18.model

data class Instruction(val opcode: String, val a: Int, val b: Int, val c: Int) {
    fun invoke(state: RegisterState) {
        state[c] = opcodes[opcode]!!.invoke(state, a, b)
    }
}

data class InstructionPointer(val binding: Int, var value: Int = 0) {
    fun update(newValue: Int): Int {
        value = newValue + 1
        return value
    }
}