package com.github.dzkoirn.adventofcode2022.day5

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    val lines = readInput("day_5_input")
    println("Start")
    println(puzzle1(lines))
    println("-------------")
    println(puzzle2(lines))
    println("Finish")
}

fun puzzle1(lines: List<String>): String {
    val input = parseInput(lines)
    val result = executeCommands(input)
    return result.convertToResultString()
}

fun puzzle2(lines: List<String>): String {
    val input = parseInput(lines)
    val result = executeCommands2(input)
    return result.convertToResultString()
}

fun List<List<Char>>.convertToResultString() =
    this.map { it.last() }.joinToString(separator = "")

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
    return lines.map { it.split(" ") }
        .map { Input.Command(
            count = it[1].toInt(),
            from = it[3].toInt(),
            to = it[5].toInt()
        ) }
}

fun executeCommands(input: Input): List<List<Char>> {
    return input.commands.fold(input.stacks.map { it.toMutableList() }) { acc, command ->
        repeat(command.count) {
           val e = acc[command.from - 1].removeLast()
           acc[command.to - 1].add(e)
        }
        acc
    }
}

fun executeCommands2(input: Input): List<List<Char>> {
    return input.commands.fold(input.stacks.map { it.toMutableList() }) { acc, command ->
        val movingItems = acc[command.from - 1].takeLast(command.count)
        acc[command.to - 1].addAll(movingItems)
        repeat(command.count) {
            acc[command.from - 1].removeLast()
        }
        acc
    }
}
