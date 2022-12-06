package com.github.dzkoirn.adventofcode2022.day5

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    val lines = readInput("day_5_input")
}

data class Input(
    val stacks: List<List<Char>>,
    val commands: List<Command>
) {
    data class Command(
        val count: Int,
        val from: Int,
        val to: Int
    )
}

fun parseInput(lines: List<String>): Input {
    val blankLineIndex = lines.indexOfFirst { it.isBlank() }
    return Input(
        parseStacks(lines.subList(0, blankLineIndex)),
        parseCommands(lines.subList(blankLineIndex + 1, lines.size))
    )
}

fun String.align(size: Int) =
    if (length > size) {
        toCharArray()
    } else {
        toCharArray(CharArray(size) { ' ' })
    }

fun parseStacks(lines: List<String>): List<List<Char>> {
    val reversed = lines.reversed()
    val indices = reversed.first()
        .foldIndexed(mutableListOf<Int>()) { index, acc, c ->
            acc.apply {
                if (c.isDigit()) {
                    acc.add(index)
                }
            }
        }
   val maxSize = indices.last() + 1
   return reversed.subList(1, reversed.size)
        .map { it.align(maxSize).slice(indices).toCharArray() }
        .fold(Array(indices.size) { mutableListOf<Char>() }) {
            acc, chars ->
            chars.forEachIndexed { index, c ->
                if (c.isLetter()) {
                    acc[index].add(c)
                }
            }
            acc
        }.map { it.toList() }
}

fun parseCommands(lines: List<String>): List<Input.Command> {
    return emptyList()
}