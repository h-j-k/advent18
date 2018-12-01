package com.ikueb.advent18

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

fun getInput(clazz: Class<Any>): List<String> {
    try {
        return Files.readAllLines(Paths.get("build", "resources", "test",
                clazz.simpleName + ".txt"))
    } catch (e: IOException) {
        throw RuntimeException("number of lines but got: " + e.message)
    }
}

fun getSingleInput(clazz: Class<Any>): String {
    try {
        return Files.readAllLines(Paths.get("build", "resources", "test",
                clazz.simpleName + ".txt"))
                .iterator().next()
    } catch (e: IOException) {
        throw RuntimeException("line but got: " + e.message)
    }
}