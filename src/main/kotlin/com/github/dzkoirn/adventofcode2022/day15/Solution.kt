package com.github.dzkoirn.adventofcode2022.day15

import com.github.dzkoirn.adventofcode2022.Input
import com.github.dzkoirn.adventofcode2022.Point
import com.github.dzkoirn.adventofcode2022.readInput
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    println("Day 15. Start")
    val input = readInput("day_15_input")
    println("Puzzle 1")
    val start = System.nanoTime()
//    val result = getCoverageOnLineSlow(2000000, input)
    val result = getCoverageOnLinev2(2000000, input)
    val end = System.nanoTime()
    println(result)
    println("It took ${Duration.ofNanos(end - start).toSeconds()} seconds )))")
    println("==================================")
    println("Puzzle 2")
    val start2 = System.nanoTime()
    val result2 = findNotCoveredIntArea(input, 4000000)
    val end2 = System.nanoTime()
    println(result2.first().let { (x, y) -> x.toLong() * 4000000 + y.toLong() })
    println("It took ${Duration.ofNanos(end2 - start2).toSeconds()} seconds )))")
    println("Finish")
}

typealias Sensor = Point
typealias Beacon = Point
fun parseInput(input: Input): List<Pair<Sensor, Beacon>> =
    input.map { stupidLineParsing(it) }

private fun stupidLineParsing(str: String): Pair<Sensor, Beacon> {
    fun parseValues(values: String): Pair<Int, Int> {
        return values.split(",")
            .map { it.trim() }
            .map { it.split("=").last().toInt() }
            .let { (x, y) -> Pair(x, y) }
    }
    return str.split(":")
        .map { it.trim() }
        .let { (left, right) ->
            Pair(
                first = with(left.substring("Sensor at ".length)) {
                    parseValues(this).let { (x, y) ->
                        Sensor(x = x, y = y)
                    }
                },
                second = with(right.substring("closest beacon is at ".length)) {
                    parseValues(this).let { (x, y) ->
                        Beacon(x = x, y = y)
                    }
                }
            )
        }
}

internal infix fun Point.calculateManhattanDistance(other: Point): Int =
    (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue

fun Point.calculateCoveredAreaWithDistance(distance: Int): Sequence<Point> {
    val pointX = this.x
    val pointY = this.y
    return sequence {
        for (xx in (pointX - distance .. pointX + distance)) {
            val dy = distance - (pointX - xx).absoluteValue
            for (yy in (pointY - dy .. pointY + dy)) {
                yield(Point(xx, yy))
            }
        }
    }
}

fun modelCoverage(report: List<Pair<Sensor, Beacon>>): Sequence<Point> {
    return report.asSequence()
        .flatMap { (sensor, beacon) ->
            val distance = sensor calculateManhattanDistance beacon
            sensor.calculateCoveredAreaWithDistance(distance)
        }
}

fun getCoverageOnLineSlow(
    lineNumber: Int,
    input: Input
): Int {
    val report = parseInput(input)
    val coveredPoints = modelCoverage(report)
    val occupiedPoints = report.map { (s, b) -> listOf(s, b) }
        .flatten()
        .toSet()
    return coveredPoints.filter { (_, y) -> y == lineNumber }
        .filterNot { it in occupiedPoints }
        .distinct()
        .count()
}

fun Pair<Sensor, Beacon>.calculateCoverageForLine(yLine: Int): Set<Point> {
    return this.calculateCoverageRangeForLine(yLine).map { x -> Point(x, yLine) }
        .toSet()
}

fun Pair<Sensor, Beacon>.calculateCoverageRangeForLine(yLine: Int): IntRange {
    val distance = first calculateManhattanDistance second
    val yRange = (first.y - distance..first.y + distance)
    return if (yLine in yRange) {
        val dx = distance - (yLine - first.y).absoluteValue
        ((first.x - dx)..(first.x + dx))
    } else {
        IntRange.EMPTY
    }
}

fun getCoverageOnLinev2(
    lineNumber: Int,
    input: Input
): Int {
    val report = parseInput(input)
    val occupiedPoints = report.map { (s, b) -> listOf(s, b) }
        .flatten()
        .toSet()
    val coveredPoints = report.map { pair -> pair.calculateCoverageForLine(lineNumber) }
        .flatten()
        .toSet()
//        .also { println("Debug print covered points: ${it.sortedWith(PointComparator())}") }
    return coveredPoints.filter { (_, y) -> y == lineNumber }
        .filterNot { it in occupiedPoints }
        .distinct()
        .count()
}

fun List<IntRange>.merge(): List<IntRange> {
    return this.filterNot { it.isEmpty() }
        .sortedBy { it.first }
        .fold(mutableListOf<IntRange>()) { acc, intRange ->
            if (acc.isEmpty()) {
                acc.apply {
                    add(intRange)
                }
            } else {
                val previousRange = acc.removeLast()
                val delta = min(previousRange.last, intRange.last) - max(previousRange.first, intRange.first)
                if (delta >= 0) {
                    val newRange = IntRange(min(previousRange.first, intRange.first),  max(previousRange.last, intRange.last))
                    acc.apply {
                        add(newRange)
                    }
                } else {
                    acc.apply {
                        add(previousRange)
                        add(intRange)
                    }
                }
            }
    }
}

fun findNotCoveredIntArea(input: Input, maxY: Int): Set<Point> {
    val report = parseInput(input)
//    val occupiedPoints = report.map { (s, b) -> listOf(s, b) }
//        .flatten()
//        .toSet()
    val set = mutableSetOf<Point>()
    for (line in (0..maxY)) {
        val ranges = report.map { pair -> pair.calculateCoverageRangeForLine(line) }.merge()
        if (ranges.size > 1) {
            set.add(Point(ranges.first().last +1, line))
        }
    }
    return set
}