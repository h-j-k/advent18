package com.ikueb.advent18

object Day13 {

    fun getFirstCollision(input: List<String>): Point {
        val state = input.mapIndexed { i, line -> Line(i, line) }
        val carts = state.flatMap { it.carts }.toSet()
        while (carts.getCollisions().isEmpty()) {
            carts.sortedWith(compareBy({ it.point.y }, { it.point.x }))
                    .forEach {
                        it.nextPoint(state)
                        if (carts.collidedAt(it).isNotEmpty()) {
                            it.active = false
                            carts.collidedAt(it)
                                    .forEach { cart -> cart.active = false }
                        }
                    }
        }
        return carts.getCollisions().keys
                .sortedWith(compareBy({ it.y }, { it.x }))
                .first()
    }
}

private typealias Carts = Set<Cart>

private fun Carts.getCollisions() = filter { !it.active }.groupBy { it.point }

private fun Carts.collidedAt(cart: Cart) = filter { it != cart && cart.at(it) }

private data class Line(val carts: Set<Cart>, val track: String) {

    constructor(row: Int, input: String) :
            this(getCarts(row, input), getTrack(input))

    fun get(column: Int) = track[column]

    companion object {
        fun getCarts(row: Int, input: String) = mutableSetOf<Cart>().apply {
            input.forEachIndexed { i, track ->
                when (track) {
                    '^' -> add(Cart(Point(i, row), Direction.NORTH))
                    '>' -> add(Cart(Point(i, row), Direction.EAST))
                    'v' -> add(Cart(Point(i, row), Direction.SOUTH))
                    '<' -> add(Cart(Point(i, row), Direction.WEST))
                }
            }
        }.toSet()

        fun getTrack(input: String) = StringBuilder(input).apply {
            input.forEachIndexed { i, track ->
                when (track) {
                    '<', '>' -> setCharAt(i, '-')
                    '^', 'v' -> setCharAt(i, '|')
                }
            }
        }.toString()
    }
}

private data class Cart(var point: Point,
                        var direction: Direction,
                        var turn: Turn? = null,
                        var active: Boolean = true) {

    fun nextPoint(state: List<Line>) {
        if (!active) return
        point = when (direction) {
            Direction.NORTH -> point.n()
            Direction.EAST -> point.e()
            Direction.SOUTH -> point.s()
            Direction.WEST -> point.w()
        }
        when (val track = state[point.y].track[point.x]) {
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