package com.ikueb.advent18

import com.ikueb.advent18.model.*
import java.util.*

object Day15 {

    fun getBattleOutcome(input: List<String>): Int {
        val state: Cave = input.asInputMap(getPlayers(), getCaveMap())
        val players = state.getTokens()
        var completedRounds = 0
        while (players.gameContinues()) {
            var isCompleteRound = true
            for (player in players.activeAndSorted()) {
                if (!players.gameContinues()) {
                    isCompleteRound = false
                    break
                }
                var target = players.getAttackTarget(player)
                if (target == null) {
                    player.move(state, players.openingsFor(player, state))
                            .firstOrNull()?.let { player.point = it }
                    target = players.getAttackTarget(player)
                }
                target?.let { player.attack(it) }

            }
            if (isCompleteRound) completedRounds++
        }
        return completedRounds * players.active().sumBy { it.hit }
    }
}

private typealias Cave = List<InputLine<Player>>

private fun Cave.isOpen(point: Point) =
        at(point) == '.' && getTokens().active().none { it.point == point }

private fun Point.orderedOpenCardinals(state: Cave) =
        listOf(n(), w(), e(), s()).filter(state::isOpen)

private typealias Players = InputTokens<Player>

private fun Players.gameContinues() = active().groupBy { it.role }.size != 1

private fun Players.getAttackTarget(player: Player) =
        filter(player::isAttackTarget)
                .sortedWith(compareBy({ it.hit }, { it.point }))
                .firstOrNull()

private fun Players.openingsFor(player: Player, state: Cave) =
        filter(player::isTarget)
                .flatMap { it.point.orderedOpenCardinals(state) }
                .toSet()

private fun getPlayers(): (Int, String) -> Set<Player> = { row, input ->
    mutableSetOf<Player>().apply {
        input.forEachIndexed { i, position ->
            when (position) {
                'E' -> add(Player(Point(i, row), Role.ELF))
                'G' -> add(Player(Point(i, row), Role.GOBLIN))
            }
        }
    }.toSet()
}

private fun getCaveMap(): (String) -> String = { input ->
    input.replace("[EG]".toRegex(), ".")
}

private data class Player(override var point: Point,
                          val role: Role,
                          var attack: Int = 3,
                          var hit: Int = 200) : InputToken(point) {

    override fun isActive() = hit > 0

    fun isTarget(other: Player) = isActive() && other.isActive()
            && role != other.role

    fun isAttackTarget(other: Player) = isTarget(other)
            && point.manhattanDistance(other.point) == 1

    fun attack(other: Player) {
        if (isAttackTarget(other)) other.attacked(attack)
    }

    private fun attacked(hit: Int) {
        this.hit -= hit
    }

    fun move(state: Cave, openings: Set<Point>): List<Point> {
        if (!isActive()) return emptyList()
        val seen = mutableSetOf(point)
        with(ArrayDeque<List<Point>>().apply {
            point.orderedOpenCardinals(state).forEach { add(listOf(it)) }
        }) {
            while (isNotEmpty()) {
                val path = removeFirst()
                val end = path.last()
                when {
                    end in openings -> return path
                    seen.add(end) -> end.orderedOpenCardinals(state)
                            .filterNot { it in seen }
                            .forEach { add(path + it) }
                }
            }
        }
        return emptyList()
    }
}

private enum class Role {
    ELF,
    GOBLIN;
}