package com.github.dzkoirn.adventofcode2022.day11

import com.github.dzkoirn.adventofcode2022.readInput
import java.util.*

fun main() {
    val input = readInput("day_11_input")
}

internal data class Monkey(
    val id: Int,
    val items: MutableList<Int> = mutableListOf(),
    val operation: (Int) -> Int,
    val test: Int,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int
)

internal fun parseInput(input:List<String>): List<Monkey> {
    input.windowed(size = 6, step = 7)
        .map { m ->
            println(Scanner(m[0]).nextInt())
//            Monkey(
//                id = Scanner(m[0]).nextInt(),
//                items = Scanner(m[1]).
//            )
        }
    return emptyList()
}

