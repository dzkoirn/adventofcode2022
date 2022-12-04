package com.github.dzkoirn.adventofcode2022.day3

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    println("Start")
    println(puzzle1(readInput("day_3_input")))
    println("-----------------------")
    println(puzzle2(readInput("day_3_input")))
    println("Finish")
}

internal fun String.divide(): Pair<String, String> {
    val median = length / 2
    return Pair(
        first = substring(0, median),
        second = substring(median))
}

internal fun mapCharacterToScore(character: Char) =
    if (character.isLowerCase()) {
        character - 'a' + 1
    } else {
        character - 'A' + 27
    }

internal fun findDublicateChar(first: String, second: String): Char {
    val set = first.asIterable().intersect(second.asIterable().toSet())
    assert(set.size == 1)
    return set.first()
}


internal fun findDuplicatesItems(lines: List<String>): List<Char> =
    lines.map { it.divide() }
        .map { (first, second) -> findDublicateChar(first, second) }

internal fun puzzle1(lines: List<String>): Int {
    println("Puzzle 1")
    return findDuplicatesItems(lines).sumOf { mapCharacterToScore(it) }
}

internal fun findIntersection(lines: List<String>): Char =
    lines.map { it.asIterable() }.reduce{ acc, chars ->
        acc.intersect(chars)
    }.first()

internal fun puzzle2(lines: List<String>): Int {
    println("Puzzle 2")
    return lines.windowed(3, 3)
        .map { lll -> findIntersection(lll) }
        .sumOf { mapCharacterToScore(it) }
}