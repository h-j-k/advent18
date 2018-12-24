package com.ikueb.advent18.model

data class Instruction(val opcode: String, val a: Int, val b: Int, val c: Int) {
    fun invoke(state: RegisterState) {
        state[c] = opcodes[opcode]!!.invoke(state, a, b)
    }
}

data class InstructionPointer(val binding: Int) {

    private var value = 0

    fun update(registerState: RegisterState): Int {
        value = registerState[binding] + 1
        return value
    }
}