package com.ikueb.advent18

import com.ikueb.advent18.model.*

object Day13 {

    fun getFirstCollision(input: List<String>) =
            process(input) { it.getCollisions().isEmpty() }
                    .getCollisions().keys
                    .sorted()
                    .first()

    fun getLastCartStanding(input: List<String>) =
            process(input) { it.count(Cart::active) > 1 }
                    .find { it.active }!!.point

    private fun process(input: List<String>,
                        loopPredicate: (Carts) -> Boolean): Carts {
        val state = input.asInputMap(getCarts(), getTrack())
        val carts = state.getTokens()
        while (loopPredicate(carts)) {
            carts.activeAndSorted().forEach {
                it.nextPoint(state)
                with(carts.collidedAt(it)) {
                    if (isNotEmpty()) {
                        it.active = false
                        forEach { cart -> cart.active = false }
                    }
                }
            }
        }
        return carts
    }
}

private typealias Carts = InputTokens<Cart>

private fun Carts.getCollisions() = filter { !it.active }.groupBy { it.point }

private fun Carts.collidedAt(cart: Cart) = filter { it != cart && cart.at(it) }

private fun getCarts(): (Int, String) -> Set<Cart> = { row, input ->
    mutableSetOf<Cart>().apply {
        input.forEachIndexed { i, track ->
            when (track) {
                '^' -> add(Cart(Point(i, row), Direction.NORTH))
                '>' -> add(Cart(Point(i, row), Direction.EAST))
                'v' -> add(Cart(Point(i, row), Direction.SOUTH))
                '<' -> add(Cart(Point(i, row), Direction.WEST))
            }
        }
    }.toSet()
}

private fun getTrack(): (String) -> String = { input ->
    StringBuilder(input).apply {
        input.forEachIndexed { i, track ->
            when (track) {
                '<', '>' -> setCharAt(i, '-')
                '^', 'v' -> setCharAt(i, '|')
            }
        }
    }.toString()
}

private data class Cart(override var point: Point,
                        var direction: Direction,
                        var turn: Turn? = null,
                        var active: Boolean = true) : InputToken(point) {

    override fun isActive() = active

    fun nextPoint(state: InputMap<Cart>) {
        if (!active) return
        point = when (direction) {
            Direction.NORTH -> point.n()
            Direction.EAST -> point.e()
            Direction.SOUTH -> point.s()
            Direction.WEST -> point.w()
        }
        when (val track = state.at(point)) {
            '\\', '/' -> direction = direction.corner(track)
            '+' -> Turn.next(turn, direction)
                    .let { (nextTurn, nextDirection) ->
                        turn = nextTurn
                        direction = nextDirection
                    }
        }
    }

    fun at(other: Cart) = active && other.active && point == other.point
}

private enum class Turn {
    LEFT,
    STRAIGHT,
    RIGHT;

    companion object {
        fun next(current: Turn?, direction: Direction) =
                (if (current == null) LEFT
                else values()[(current.ordinal + 1) % 3])
                        .let { it to direction.turn(it) }
    }
}

private enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun turn(turn: Turn) = when (turn) {
        Turn.LEFT -> values()[(ordinal + 3) % 4]
        Turn.STRAIGHT -> this
        Turn.RIGHT -> values()[(ordinal + 1) % 4]
    }


    fun corner(track: Char) = when (track) {
        '\\' -> when (this) {
            NORTH, SOUTH -> turn(Turn.LEFT)
            else -> turn(Turn.RIGHT)
        }
        '/' -> when (this) {
            NORTH, SOUTH -> turn(Turn.RIGHT)
            else -> turn(Turn.LEFT)
        }
        else -> throw IllegalArgumentException("Unknown corner.")
    }
}