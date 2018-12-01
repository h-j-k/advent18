package com.ikueb.advent18

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

fun getInput(file: String): List<String> {
    try {
        return Files.readAllLines(Paths.get("build", "resources", "test", "$file.txt"))
    } catch (e: IOException) {
        throw RuntimeException("number of lines but got: " + e.message)
    }
}

fun getSingleInput(file: String): String {
    try {
        return Files.readAllLines(Paths.get("build", "resources", "test", "$file.txt"))
                .iterator().next()
    } catch (e: IOException) {
        throw RuntimeException("line but got: " + e.message)
    }
}