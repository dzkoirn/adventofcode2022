package com.github.dzkoirn.adventofcode2022.day15

import com.github.dzkoirn.adventofcode2022.Input
import com.github.dzkoirn.adventofcode2022.Point
import com.github.dzkoirn.adventofcode2022.PointComparator
import com.github.dzkoirn.adventofcode2022.readInput
import kotlin.math.absoluteValue

fun main() {
    println("Day 15. Start")
    val input = readInput("day_15_input")
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

fun Point.calculateCoveredAreaWithDistance(distance: Int): Set<Point> {
    return (this.x - distance .. this.x + distance).map { xx ->
        val dy = distance - (this.x - xx).absoluteValue
        (this.y - dy .. this.y + dy).map { yy ->
            Point(xx, yy)
        }
    }.flatten()
    .toSet()
}

fun modelCoverage(report: List<Pair<Sensor, Beacon>>): Set<Point> {
    return report.map { (sensor, beacon) ->
        val distance = sensor calculateManhattanDistance beacon
        sensor.calculateCoveredAreaWithDistance(distance)
    }.flatten()
    .toSet()
}

fun getCoverageOnLine(lineNumber: Int, coveredPoints: Set<Point>, report: List<Pair<Sensor, Beacon>>): Int {
    return coveredPoints.filter { (_, y) -> y == lineNumber }
        .also { println("Debug ${it.sortedWith(PointComparator())}") }
        .filterNot { it in report  }
        .count()
}