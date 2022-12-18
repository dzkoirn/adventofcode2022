package com.github.dzkoirn.adventofcode2022.day14

import com.github.dzkoirn.adventofcode2022.*
import java.lang.Integer.min
import java.util.Scanner
import java.util.stream.Collectors
import kotlin.math.max
import kotlin.math.sign
import kotlin.streams.asSequence

fun main() {
    println("Start. Day 14")
    val input = readInput("day_14_input")
    println("Puzzle 1")
    println(runSimulation(parseInput(input)))
    println("==================================================")
    println("Puzzle 2")
    println(runSimulation(cave = parseInput(input), withFloor = true))
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

fun debugPrintCave(cave: Cave, sands: Set<Point> = emptySet(), withFloor: Boolean = false): String {
    val (caveStart, caveEnd) = cave.size()
    println("Cave size: from $caveStart till $caveEnd withFloor $withFloor")
    val width: Int
    val matrixStart: Int
    if (sands.isEmpty()) {
        width = caveEnd.x - caveStart.x + 1
        matrixStart = caveStart.x
    } else {
        val (sandsStarts, sandsEnd) = sands.stream().collect(
            Collectors.teeing(
                Collectors.mapping({ (x, _) -> x }, Collectors.toUnmodifiableList()),
                Collectors.mapping({ (_, y) -> y }, Collectors.toUnmodifiableList())
            ) { xs, ys ->
                val xsorted = xs.sorted()
                val ysorted = ys.sorted()
                listOf(Point(xsorted.first(), ysorted.first()), Point(xsorted.last(), ysorted.last()))
            }
        )
        println("Sands size: from $sandsStarts till $sandsEnd withFloor $withFloor")
        matrixStart = min(caveStart.x, sandsStarts.x)
        width = max(caveEnd.x, sandsEnd.x) - matrixStart + 1
    }
    val height = (caveEnd.y + 1).let { maxY -> if (withFloor) maxY + 2 else maxY }
    val matrix = createMatrix(
        width = width,
        height = height, '.')
    for ((x, y) in cave) {
        matrix.put(x - matrixStart, y, '#')
    }
    for ((x, y) in sands) {
        matrix.put(x - matrixStart, y, 'o')
    }
    if (withFloor) {
        repeat(width) {
            matrix.put(it, height - 1, '#' )
        }
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

fun runSimulation(cave: Cave, start: Point = Point(500, 0), withFloor: Boolean = false): Int {
    val sands = mutableSetOf<Point>()
    val floorLevel = if (withFloor) cave.floorLevel() else - 1
    var currentUnit: Point = start
    fun couldContinue(): Boolean {
       return  if (withFloor) {
            true
        } else {
            currentUnit.inBoundsOf(cave)
        }
    }
    do {
        var nextPoint = currentUnit.copy(y = currentUnit.y + 1)
        if (withFloor) {
            if (nextPoint.y == floorLevel) {
                sands.add(currentUnit)
                currentUnit = start
                continue
            }
        }
        if ((nextPoint in cave) or (nextPoint in sands)) {
            nextPoint = currentUnit.copy(x = currentUnit.x - 1, y = currentUnit.y + 1)
            if ((nextPoint in cave) or (nextPoint in sands)) {
                nextPoint = currentUnit.copy(x = currentUnit.x + 1, y = currentUnit.y + 1)
                if ((nextPoint in cave) or (nextPoint in sands)) {
                    if (sands.add(currentUnit)) {
                        currentUnit = start
                        continue
                    } else {
                        break
                    }
                }
            }
        }
        currentUnit = nextPoint
    } while (couldContinue())
    debugPrintCave(cave, sands, withFloor)
    return sands.size
}

fun Cave.floorLevel(): Int =
    this.size().let { (_, end) -> end }.y + 2
