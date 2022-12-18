package com.github.dzkoirn.adventofcode2022.day14

import com.github.dzkoirn.adventofcode2022.*
import java.util.Scanner
import kotlin.math.sign
import kotlin.streams.asSequence

fun main() {
    println("Start. Day 14")
    val input = readInput("day_14_input")
    println(runSimulation(parseInput(input)))
    println("Finish")
}

typealias Cave = Set<Point>

fun Cave.size(): Pair<Point, Point> {
    val (minX, _) = this.first()
    val (maxX, _) = this.last()
    val (_, maxY) = this.maxBy { (_, y) -> y }
    return Pair(Point(minX,0), Point(maxX, maxY))
}

fun parseInput(input: List<String>): Cave {
    return input.map { line -> Scanner(line)
        .useDelimiter("->")
        .tokens()
        .map { str -> str.trim()
            .split(',')
            .let { (l, r) -> Point(x = l.toInt(), y = r.toInt())  }
        }.asSequence()
        .windowed(size = 2)
        .map { (s, e) -> line(s, e) }
        .flatten()
        .asIterable()
     }.flatten()
    .toSortedSet(PointComparator())
}

private fun line(start: Point, end: Point): Set<Point> {
    data class Direction(val x: Int, val y: Int)
    val dir = Direction(x = (end.x - start.x).sign, y = (end.y - start.y).sign)
    var iterator = start.copy()
    val set = mutableSetOf(start)
    do {
        iterator = iterator.copy(x = iterator.x + dir.x, y = iterator.y + dir.y)
        set.add(iterator)
    } while (iterator != end)
    return set
}

fun debugPrintCave(cave: Cave, sands: Set<Point> = emptySet()): String {
    val (start, end) = cave.size()
    println("Cave size: from $start till $end")
    val matrix = createMatrix(end.x - start.x + 1, end.y + 1, '.')
    for ((x, y) in cave) {
        matrix.put(x - start.x, y, '#')
    }
    for ((x, y) in sands) {
        matrix.put(x - start.x, y, 'o')
    }
    return matrix.joinToString(separator = "\n") { it.joinToString(separator = "") }.also {
        println(it)
    }
}

private fun Point.inBoundsOf(cave: Cave): Boolean {
    val (start, end) = cave.size()
    return (this.x >= start.x) && (this.x <= end.x) &&
            (this.y >= start.y) && (this.y <= end.y)
}

fun runSimulation(cave: Cave, start: Point = Point(500, 0)): Int {
    val sands = mutableSetOf<Point>()
    var currentUnit: Point = start
    do {
        var nextPoint = currentUnit.copy(y = currentUnit.y + 1)
        if ((nextPoint in cave) or (nextPoint in sands)) {
            nextPoint = currentUnit.copy(x = currentUnit.x - 1, y = currentUnit.y + 1)
            if ((nextPoint in cave) or (nextPoint in sands)) {
                nextPoint = currentUnit.copy(x = currentUnit.x + 1, y = currentUnit.y + 1)
                if ((nextPoint in cave) or (nextPoint in sands)) {
                    sands.add(currentUnit)
                    currentUnit = start
                    continue
                }
            }
        }
        currentUnit = nextPoint
    } while (currentUnit.inBoundsOf(cave) )
    debugPrintCave(cave, sands)
    return sands.size
}