package com.github.dzkoirn.adventofcode2022.day15

import com.github.dzkoirn.adventofcode2022.Input
import com.github.dzkoirn.adventofcode2022.Point
import com.github.dzkoirn.adventofcode2022.readInput
import java.time.Duration
import kotlin.math.absoluteValue

import kotlin.time.toDuration

fun main() {
    println("Day 15. Start")
    val input = readInput("day_15_input")
    println("Puzzle 1")
    val start = System.nanoTime()
    val result = getCoverageOnLineSlow(2000000, input)
//    val result = getCoverageOnLinev2(2000000, input)
    val end = System.nanoTime()
    println(result)
    println("It take ${Duration.ofNanos(end - start).toSeconds()} seconds )))")
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
    val distance = first calculateManhattanDistance second
    val yRange = (first.y - distance..first.y + distance)
    return if (yLine in yRange) {
        val dx = distance - (yLine - first.y).absoluteValue
        ((first.x - dx)..(first.x + dx)).map { x ->
            Point(x, yLine)
        }.toSet()
    } else {
        emptySet()
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