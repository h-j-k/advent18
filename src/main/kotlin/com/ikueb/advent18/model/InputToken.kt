package com.ikueb.advent18.model

abstract class InputToken(open var point: Point) {

    abstract fun isActive(): Boolean
}