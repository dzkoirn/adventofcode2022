package com.github.dzkoirn.adventofcode2022.day9

import com.github.dzkoirn.adventofcode2022.readInput
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val input = readInput("day_9_input")
    println("Start")
    println("Puzzle 1")
    println(countTailVisitedPoints(
        lines = input,
        start = Pair(0, 0),
        knotSize = 2
    ))
    println("===================")
    println("Puzzle 2")
    println(countTailVisitedPoints(
        lines = input,
        start = Pair(0, 0),
        knotSize = 10
    ))
    println("Finish")
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN;

    companion object {
        fun fromString(string: String): Direction {
            return when(string) {
                "R" -> RIGHT
                "L" -> LEFT
                "U" -> UP
                "D" -> DOWN
                else -> throw IllegalArgumentException("No such directions $string")
            }
        }
    }
}

data class Command(
    val direction: Direction,
    val steps: Int
)

internal fun parseInput(lines: List<String>): List<Command> {
    return lines.map { line -> line.split(" ") }
        .map { (direction, steps) ->
            Command(
                direction = Direction.fromString(direction),
                steps = steps.toInt()
            )
        }
}

fun countTailVisitedPoints(
    lines: List<String>,
    start: Knot,
    knotSize: Int
): Int {
    val commands = parseInput(lines)
    val set = mutableSetOf<Knot>()
    executeCommands(
        start = start,
        size = knotSize,
        commands = commands
    ) { rope -> set.add(rope.last()) }
    return set.size
}

internal fun executeCommands(
    start: Knot,
    size: Int,
    commands: List<Command>,
    doOnEachStep: (List<Knot>) -> Unit = { }
): List<Knot> {
    var rope = Array(size) { start }.toList()
    for ((direction, steps) in commands) {
        repeat(steps) {
            rope = moveRope(rope, direction).also {
                doOnEachStep(it)
            }
        }
    }
    return rope
}

internal typealias Knot = Pair<Int, Int>
internal val Knot.x: Int
    get() = this.first
internal val Knot.y: Int
    get() = this.second

internal fun moveRope(rope: List<Knot>, direction: Direction): List<Knot> {
    val head = rope.first()
    val newHead = when(direction) {
        Direction.LEFT -> head.copy(first = head.x - 1)
        Direction.RIGHT -> head.copy(first = head.x + 1)
        Direction.UP -> head.copy(second = head.y + 1)
        Direction.DOWN -> head.copy(second = head.y - 1)
    }
    return rope.drop(1).fold(mutableListOf(newHead)) { acc, knot ->
        val prev = acc.last()
        if (prev isNotNear knot) {
            knot.copy(
                first = knot.x + calculateStep(prev.x, knot.x),
                second = knot.y + calculateStep(prev.y, knot.y)
            )
        } else {
            knot
        }.also { acc.add(it) }
        acc
    }
}

private infix fun Knot.isNotNear(other: Knot): Boolean =
    maxOf((this.x - other.x).absoluteValue, (this.y - other.y).absoluteValue) > 1

private fun calculateStep(h: Int, t: Int): Int {
    return if (h == t) {
        0
    } else {
        (h - t).sign * 1
    }
}