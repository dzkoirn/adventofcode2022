package com.github.dzkoirn.adventofcode2022.day12

import com.github.dzkoirn.adventofcode2022.readInput

fun main() {
    val input = readInput("day_12_input")
    println("Start")
    println("Puzzle 1")
    println(findPathAndCountSteps(input))
    println("===================")
    println("Puzzle 2")
    println(findBetterStartAndCountSteps(input))
    println("Finish")
}


fun findPathAndCountSteps(input: List<String>) : Int {
    val matrix = input.map { it.toCharArray() }
    println("Debug: matrix size : ${matrix.first().size}, ${matrix.size} ")

    val start = matrix.find('S')
    println("Debug: start = $start")

    return bfs(matrix = matrix, target = 'E', candidates = setOf(start))
}

fun List<CharArray>.find(char: Char): Pair<Int, Int> {
    for ((y, row) in this.withIndex()) {
        for ((x, c) in row.withIndex()) {
            if (c == char) {
                return Pair(x, y)
            }
        }
    }
    throw NoSuchElementException("$char not found")
}

fun List<CharArray>.get(p: Pair<Int, Int>) =
    this[p.second][p.first]

fun List<CharArray>.neighbour(p: Pair<Int, Int>): List<Pair<Int, Int>> {
    val maxY = this.size
    val maxX = this[0].size
    val x = p.first
    val y = p.second
    return listOf(
        Pair(x-1, y),
        Pair(x+1, y),
        Pair(x, y-1),
        Pair(x, y+1),
    ).filter { (x, y) -> x in (0 until maxX) && y in (0 until maxY) }
}

fun Char.map(
    start: Char = 'S',
    end: Char = 'E'
) = when(this) {
    start -> 'a' - 1
    end -> 'z' + 1
    else -> this
}

tailrec fun bfs(
    matrix: List<CharArray>,
    target: Char,
    candidates: Set<Pair<Int, Int>> = emptySet(),
    visited: MutableSet<Pair<Int, Int>> = mutableSetOf(),
    iteration: Int = 0,
    previousMin: Int = -1
): Int {
    if (previousMin > -1 && iteration > previousMin) {
        return -1
    }
    if (candidates.isEmpty()) {
        return -1
    }
    val newCandidates = candidates.map { cord ->
        visited.add(cord)
        val element = matrix.get(cord)
        if (element == target) {
            return iteration
        }
        matrix.neighbour(cord)
//            .onEach {
//                println("Debug: neighbour for $cord : $it, element = ${matrix.get(it)}")
//            }
            .filter { it !in visited }
            .filter {
                val newElement = matrix.get(it)
                newElement.map() - element.map() <= 1
            }
    }.flatten()
    .toSet()
//    println("Debug: iteration=$iteration, New candidates: ${newCandidates.joinToString { "$it - ${matrix.get(it)}" }}")
//    println("Visited places:\n ${debugPrintVisitedPlaces(matrix, visited, newCandidates)}")
    return bfs(matrix, target, newCandidates, visited, iteration + 1)
}

fun debugPrintVisitedPlaces(
    m: List<CharArray>,
    visited: Set<Pair<Int, Int>>,
    newCandidates: Set<Pair<Int, Int>>
): String {
    val x = m.first().size
    val matrix = Array(m.size) { y ->
        CharArray(x) { x -> m.get(Pair(x,y))}
    }
    for (p in visited) {
        matrix[p.second][p.first] = '.'
    }
    for (p in newCandidates) {
        matrix[p.second][p.first] = '*'
    }
    return matrix.joinToString(separator = "\n") { it.joinToString("") }
}

fun findBetterStartAndCountSteps(input: List<String>) : Int {
    val matrix = input.map { it.toCharArray() }
    println("Debug: matrix size : ${matrix.first().size}, ${matrix.size} ")

    val possibleStarts = matrix[0].mapIndexed { index, c ->
        if (c == 'S' || c == 'a') {
            Pair(index, 0)
        } else {
            null
        }
    }.filterNotNull() +
    matrix.last().mapIndexed { index, c ->
        if (c == 'S' || c == 'a') {
            Pair(index, matrix.lastIndex)
        } else {
            null
        }
    }.filterNotNull() +
    matrix.mapIndexed { index, chars ->
        val f = chars.first()
        val l = chars.last()
        mutableListOf<Pair<Int, Int>>().apply {
            if (f == 'S' || f == 'a') {
                add(Pair(0, index))
            }
            if (l == 'S' || l == 'a') {
                add(Pair(chars.lastIndex, index))
            }
        }.toList()
    }.flatten()

    println("Debug: possible starts = $possibleStarts")

    var minWay = -1
    for (p in possibleStarts.toSet()) {
        val r = bfs(matrix = matrix, target = 'E', candidates = possibleStarts.toSet(), previousMin = minWay)
        if (r != -1) {
            minWay = r
        }
    }

    return minWay
}