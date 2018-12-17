package com.ikueb.advent18

import com.ikueb.advent18.model.Point
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
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

private object PointConverter : ArgumentConverter {
    override fun convert(source: Any?, context: ParameterContext?): Any {
        return source?.toString()?.let {
            "(\\d+),(\\d+)".parseWith(it) { (x, y) -> Point(x, y) }
        } ?: throw ArgumentConversionException("Unable to convert to point.")
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@ConvertWith(PointConverter::class)
internal annotation class ToPoint