package com.ikueb.advent18.model

abstract class InputToken(open var point: Point) {

    abstract fun isActive(): Boolean

    fun x() = point.x
    fun y() = point.y
    fun atColumn(x: Int) = point.y == x
    fun atRow(y: Int) = point.y == y
}