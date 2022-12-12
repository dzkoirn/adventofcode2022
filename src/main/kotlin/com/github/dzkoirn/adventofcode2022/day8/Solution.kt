package com.github.dzkoirn.adventofcode2022.day8

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    val input = readInput("day_8_input")
    println("Start")
    println("Puzzle 1")
    println(puzzle1(input))
    println("===================")
    println("Puzzle 2")
    println(puzzle2(input))
    println("Finish")
}

internal fun parseInput(lines: List<String>): List<List<Int>> =
    lines.map { line -> line.asSequence().map { ch -> ch.digitToInt() }.toList() }

fun <T> List<List<T>>.rowIteration(): List<List<T>> =
    (0..this.first().lastIndex).map { index ->
        this.map { list -> list[index] }
    }

fun List<Int>.findVisible(): List<Int> {
    data class Accumulator(
        var currentMax: Int = -1,
        val indexes: MutableList<Int> = mutableListOf()
    )
    fun checkIsVisible(acc: Accumulator, index: Int, value: Int): Accumulator {
        if (value > acc.currentMax) {
            acc.currentMax = value
            acc.indexes.add(index)
        }
        return acc
    }
    return this.foldIndexed(Accumulator()) { index, acc, value ->
        checkIsVisible(acc, index, value)
    }.indexes +
    this.foldRightIndexed(Accumulator()) { index, value, acc ->
        checkIsVisible(acc, index, value)
    }.indexes
}

fun puzzle1(lines: List<String>): Int {
    val matrix = parseInput(lines)
    val set1=  matrix.flatMapIndexed { index, ints ->
        ints.findVisible().map {
            Pair(index, it)
        }
    }.toSet()
    val set2 = matrix.rowIteration().flatMapIndexed { index, ints ->
        ints.findVisible().map {
            Pair(it, index)
        }
    }.toSet()
    return (set1 + set2).count()
}

fun puzzle2(lines: List<String>): Int {
    val matrix = parseInput(lines)
    return (1 until matrix.first().lastIndex).map { indexY ->
        (1 until matrix.lastIndex).map { indexX ->
            calculateScenicScore(matrix, indexX, indexY)
        }
    }.flatten()
    .max()
}

private fun calculateScenicScore(matrix: List<List<Int>>, indexX: Int, indexY: Int): Int {
    val startValue = matrix[indexX][indexY]
    return listOf(
        (indexY - 1 downTo 0).map { indexX to it },
        (indexY + 1 .. matrix.first().lastIndex).map { indexX to it },
        (indexX - 1 downTo 0).map { it to indexY },
        (indexX + 1 .. matrix.lastIndex).map { it to indexY }
    )
    .map { coordinates ->
        coordinates.indexOfFirst { (x, y) -> matrix[x][y] >= startValue }
            .let { if (it > -1) {
                it + 1
            } else {
                coordinates.size
            } }
    }//.also { println("for v = $startValue in ($indexX,$indexY) counts = $it") }
    .reduce { acc, i -> acc * i }
    //    .also { println("for v = $startValue in ($indexX,$indexY) score = $it") }
}