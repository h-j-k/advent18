package com.ikueb.advent18

import com.ikueb.advent18.Day11.getTopSquareTopCorner
import com.ikueb.advent18.Day11.getTopVariableSquareTopCornerSize
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

internal class Day11Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "18 | 33,45",
        "42 | 21,61"
    ])
    fun partOneExample(input: Int, @ToPoint expected: Point) {
        assertThat(getTopSquareTopCorner(input)).isEqualTo(expected)
    }

    @Test
    fun partOne() {
        assertThat(getTopSquareTopCorner(7672)).isEqualTo(Point(22, 18))
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = [
        "18 | 90,269,16",
        "42 | 232,251,12"
    ])
    fun partTwoExample(input: Int, expected: String) {
        assertThat(getTopVariableSquareTopCornerSize(input)).isEqualTo(expected)
    }

    @Test
    fun partTwo() {
        assertThat(getTopVariableSquareTopCornerSize(7672)).isEqualTo("234,197,14")
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
private annotation class ToPoint