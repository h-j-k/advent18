package com.ikueb.advent18

import com.ikueb.advent18.model.*

object Day24 {

    private const val DEFINITION =
            "(\\d+) units each with (\\d+) hit points (\\(([^)]+)\\) )?with an attack that does (\\d+) ([^ ]+) damage at initiative (\\d+)"

    fun getWinningUnits(input: List<String>) =
            battle(input).active().sumBy { it.count }

    fun getWinningImmuneUnits(input: List<String>) = generateSequence(1) { it + 1 }
            .map { battle(input, it) }
            .first { it.haveImmuneSystemWon() }
            .active()
            .sumBy { it.count }

    private fun battle(input: List<String>, immunePowerUp: Int = 0): Armies {
        val armies = parse(input, immunePowerUp).getTokens()
        while (armies.areFightingFit()) {
            val targets = mutableMapOf<ArmyGroup, ArmyGroup>()
            armies.activeAndSorted().forEach { group ->
                armies.active()
                        .filter { group.isTarget(it) }
                        .filterNot { it in targets.values }
                        .sortedWith(compareBy(
                                { group.calculateDamage(it) },
                                { it.effectivePowerForSorting() },
                                { it.initiativeForSorting() }))
                        .firstOrNull()?.let { targets[group] = it }
            }
            targets.entries
                    .sortedBy { (key, _) -> key.initiativeForSorting() }
                    .forEach { (attacker, target) -> attacker.attack(target) }
            if (targets.size == 1 && targets.entries.first().toPair()
                            .let { !it.first.willBeEffectiveAgainst(it.second) }) {
                break
            }
        }
        return armies
    }

    private fun parse(input: List<String>, immunePowerUp: Int): InputMap<ArmyGroup> {
        val split = input.lastIndexMatching { it.isEmpty() }
        return listOf(
                parseArmy(input.subList(0, split), immunePowerUp),
                parseArmy(input.drop(split + 1), immunePowerUp)
        )
    }

    private fun parseArmy(input: List<String>, immunePowerUp: Int): Army {
        val army = input[0].dropLast(1)
        val powerUp = if (army == "Immune System") immunePowerUp else 0
        return Army(input.drop(1)
                .parseWith(DEFINITION) { (n, hp, _, powers, x, xType, i) ->
                    parsePowers(powers).let {
                        ArmyGroup(army, n.toInt(), hp.toInt(),
                                it["immune"] ?: emptySet(),
                                it["weak"] ?: emptySet(),
                                x.toInt() + powerUp, xType, i.toInt())
                    }
                }.toSet(), "")
    }

    private fun parsePowers(powers: String) =
            "([^;]*)(; )?(.*)".parseFor(powers) { (x, _, y) ->
                setOf(x, y).filterNot { it.isEmpty() }
                        .map { parsePower(it) }
                        .toMap()
            }

    private fun parsePower(power: String) =
            "([^ ]+) to (.*)".parseFor(power) { (power, types) ->
                power to types.split(", ".toRegex()).toSet()
            }
}

private typealias Armies = InputTokens<ArmyGroup>

private fun Armies.areFightingFit() = active().groupBy { it.army }.size != 1

private fun Armies.haveImmuneSystemWon() = active().none { it.army == "Infection" }

private typealias Army = InputLine<ArmyGroup>

private data class ArmyGroup(
        val army: String,
        var count: Int,
        val hit: Int,
        val immuneTo: Set<String>,
        val weakTo: Set<String>,
        val attack: Int,
        val attackType: String,
        private val initiative: Int
) : InputToken(Point(-initiative, -count * attack)) {

    override fun isActive() = count > 0

    fun effectivePower() = count * attack

    fun effectivePowerForSorting() = -effectivePower()

    fun isTarget(other: ArmyGroup) = isActive() && other.isActive()
            && army != other.army && attackType !in other.immuneTo

    fun calculateDamage(other: ArmyGroup) =
            effectivePowerForSorting().toDouble() * when (attackType) {
                in other.immuneTo -> 0
                in other.weakTo -> 2
                else -> 1
            }

    fun initiativeForSorting() = -initiative

    fun willBeEffectiveAgainst(other: ArmyGroup) =
            army != other.army && effectivePower() / other.hit > 0

    fun attack(other: ArmyGroup) {
        if (isTarget(other)) other.attacked(effectivePower(), attackType)
    }

    private fun attacked(attack: Int, attackType: String) {
        if (attackType !in immuneTo) {
            val potentialUnitsDamaged =
                    (attack.toDouble() / hit) * if (attackType in weakTo) 2 else 1
            val damaged = minOf(count, potentialUnitsDamaged.toInt())
            count -= damaged
            point = Point(initiativeForSorting(), effectivePowerForSorting())
        }
    }
}