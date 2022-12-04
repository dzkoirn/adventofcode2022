package com.github.dzkoirn.adventofcode2022.day4

import com.github.dzkoirn.adventofcode2022.readInput
fun main() {
    val lines = readInput("day_4_input")
    println("Start")
    println(puzzle1(lines))
    println("--------------")
    println(puzzle2(lines))
    println("Finish")
}
internal fun mapLinesToRanges(lines: List<String>):List<Pair<IntRange, IntRange>> =
    lines.map { line ->
        line.split(",").map { r ->
            r.split("-").map {
                it.toInt()
            }
        }
        .map { (first, last) -> IntRange(first, last) }
        .let { (a, b) -> Pair(a, b) }
    }

fun IntRange.fullyIntersectsWith(other: IntRange): Boolean {
    val (biggest, smallest) = if ((last - first) > (other.last - other.first)) {
        this to other
    } else {
        other to this
    }
    return (biggest.first <= smallest.first) and (smallest.last <= biggest.last)
}

internal fun puzzle1(lines: List<String>): Int {
    println("Puzzle 1")
    return mapLinesToRanges(lines)
        .filter { (first, second) -> first.fullyIntersectsWith(second) }
        .count()
}

fun IntRange.intersectsWith(other: IntRange): Boolean {
    val r = first.coerceAtLeast(other.first) <= last.coerceAtMost(other.last)
    return r
}

internal fun puzzle2(lines: List<String>): Int {
    println("Puzzle 2")
    return mapLinesToRanges(lines)
        .filter { (first, second) -> first.intersectsWith(second) }
        .count()
}