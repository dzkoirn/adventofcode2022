package com.github.dzkoirn.adventofcode2022.day6

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    val input = readInput("day_6_input")

    println("Start")
    println(puzzle1(input.first()))
    println("---------------------")
    println(puzzle2(input.first()))
    println("Finish")
}

fun puzzle1(message: String) : Int {
    println("Puzzle 1")
    return extractInfo(message, 4)
}

fun puzzle2(message: String) : Int {
    println("Puzzle 2")
    return extractInfo(message, 14)
}

private fun extractInfo(message: String, payloadSize: Int): Int =
    message.asSequence()
        .windowed(payloadSize)
        .takeWhile { it.distinct().size != payloadSize }
        .count() + payloadSize