package com.github.dzkoirn.adventofcode2022.day2

import com.github.dzkoirn.adventofcode2022.openResourseAsStream
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.IllegalStateException

fun main() {
    val strategyGuide = readStrategyGuide()
    println("Start")
    puzzle1(strategyGuide)
    println("-----------")
    puzzle2(strategyGuide)
    println("Finish")
}

private fun readStrategyGuide(): List<Pair<Char, Char>> =
    BufferedReader(
        InputStreamReader(
            BufferedInputStream(
                openResourseAsStream("day_2_input")
            )
        )
    ).use { reader ->
        reader.lineSequence()
            .map { str ->
                str.toCharArray().let { arr ->
                    arr[0] to arr[2]
                }
            }.toList()
    }

private fun puzzle1(strategyGuide: List<Pair<Char, Char>>) {
    println("Puzzle 1")
    println(strategyGuide.sumOf { game(it.copy(second = map1(it.second))) })
}

/*
 * X for Rock, Y for Paper, and Z for Scissors.
 */
fun map1(input: Char): Char =
    when(input) {
        'X' -> 'A'
        'Y' -> 'B'
        'Z' -> 'C'
        else -> throw IllegalStateException("OOOOPPSSS!!!")
    }

fun puzzle2(strategyGuide: List<Pair<Char, Char>>) {
    println("Puzzle 2")
    println(strategyGuide.sumOf { game(it.copy(second = map2(it))) })
}

/*
 * X means you need to lose,
 * Y means you need to end the round in a draw,
 * Z means you need to win.
 */
fun map2(round: Pair<Char, Char>): Char =
    when(round) {
        'A' to 'X' -> 'C'
        'A' to 'Y' -> 'A'
        'A' to 'Z' -> 'B'
        'B' to 'X' -> 'A'
        'B' to 'Y' -> 'B'
        'B' to 'Z' -> 'C'
        'C' to 'X' -> 'B'
        'C' to 'Y' -> 'C'
        'C' to 'Z' -> 'A'
        else -> throw IllegalStateException("OOOOPPSSS!!!")
    }

/*
 * A for Rock, B for Paper, and C for Scissors.
 *
 * The score for a single round is the score for the shape you selected
 * (1 for Rock, 2 for Paper, and 3 for Scissors)
 * plus the score for the outcome of the round
 * (0 if you lost, 3 if the round was a draw, and 6 if you won).
 */
fun game(round: Pair<Char, Char>): Int {
    return when(round) {
        'A' to 'A' -> 1 + 3
        'A' to 'B' -> 2 + 6
        'A' to 'C' -> 3 + 0
        'B' to 'A' -> 1 + 0
        'B' to 'B' -> 2 + 3
        'B' to 'C' -> 3 + 6
        'C' to 'A' -> 1 + 6
        'C' to 'B' -> 2 + 0
        'C' to 'C' -> 3 + 3
        else -> throw IllegalStateException("OOOOPPSSS!!!")
    }
}
