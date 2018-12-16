package com.ikueb.advent18

object Day13 {

    fun getFirstCollision(input: List<String>): Point {
        val state = input.mapIndexed { i, line -> Line(i, line) }
        val carts = state.flatMap { it.carts }.toSet()
        while (carts.groupBy { it.point }.values.none { it.size > 1 }) {
            carts.sortedWith(compareBy({ it.point.y }, { it.point.x }))
                    .forEach { it.nextPoint(state) }
        }
        return carts.groupBy { it.point }.filterValues { it.size > 1 }.keys.first()
    }
}

private data class Line(val carts: Set<Cart>, val track: String) {

    constructor(row: Int, input: String) :
            this(getCarts(row, input), getTrack(input))

    fun get(column: Int) = track[column]

    companion object {
        fun getCarts(row: Int, input: String): Set<Cart> {
            val result = mutableSetOf<Cart>()
            input.forEachIndexed { i, track ->
                when (track) {
                    '^' -> result.add(Cart(Point(i, row), Direction.NORTH))
                    '>' -> result.add(Cart(Point(i, row), Direction.EAST))
                    'v' -> result.add(Cart(Point(i, row), Direction.SOUTH))
                    '<' -> result.add(Cart(Point(i, row), Direction.WEST))
                }
            }
            return result.toSet()
        }

        fun getTrack(input: String): String {
            val result = StringBuilder(input)
            input.forEachIndexed { i, track ->
                when (track) {
                    '<', '>' -> result.setCharAt(i, '-')
                    '^', 'v' -> result.setCharAt(i, '|')
                }
            }
            return result.toString()
        }
    }
}

private data class Cart(var point: Point,
                        var direction: Direction,
                        var turn: Turn? = null) {

    fun nextPoint(state: List<Line>) {
        point = when (direction) {
            Direction.NORTH -> point.n()
            Direction.EAST -> point.e()
            Direction.SOUTH -> point.s()
            Direction.WEST -> point.w()
        }
        when (val track = state[point.y].get(point.x)) {
            '\\', '/' -> direction = direction.corner(track)
            '+' -> Turn.next(turn, direction).let { (nextTurn, nextDirection) ->
                turn = nextTurn
                direction = nextDirection
            }
        }
    }
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