package com.github.dzkoirn.adventofcode2022.day15

import com.github.dzkoirn.adventofcode2022.Point
import com.github.dzkoirn.adventofcode2022.PointComparator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testInputParsing() {
        val result = parseInput(exampleInput)
        assertEquals(expectedParsedExampleValues, result)
    }

    @Test
    fun testModelCoverage() {
        val result = modelCoverage(parseInput(listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10"))).sortedWith(PointComparator())
        val expected = setOf(
            Point(8, -2),
            Point(7, -1), Point(8, -1), Point(9, -1),
            Point(6, 0), Point(7, 0), Point(8, 0), Point(9, 0), Point(10, 0),
            Point(5, 1), Point(6, 1), Point(7, 1), Point(8, 1), Point(9, 1), Point(10, 1), Point(11, 1),
            Point(4, 2), Point(5, 2), Point(6, 2), Point(7, 2), Point(8, 2), Point(9, 2), Point(10, 2), Point(11, 2), Point(12, 2),
            Point(3, 3), Point(4, 3), Point(5, 3), Point(6, 3), Point(7, 3), Point(8, 3), Point(9, 3), Point(10, 3), Point(11, 3), Point(12, 3), Point(13, 3),
            Point(2, 4), Point(3, 4), Point(4, 4), Point(5, 4), Point(6, 4), Point(7, 4), Point(8, 4), Point(9, 4), Point(10, 4), Point(11, 4), Point(12, 4), Point(13, 4), Point(14, 4),
            Point(1, 5), Point(2, 5), Point(3, 5), Point(4, 5), Point(5, 5), Point(6, 5), Point(7, 5), Point(8, 5), Point(9, 5), Point(10, 5), Point(11, 5), Point(12, 5), Point(13, 5), Point(14, 5), Point(15, 5),
            Point(0, 6), Point(1, 6), Point(2, 6), Point(3, 6), Point(4, 6), Point(5, 6), Point(6, 6), Point(7, 6), Point(8, 6), Point(9, 6), Point(10, 6), Point(11, 6), Point(12, 6), Point(13, 6), Point(14, 6), Point(15, 6), Point(16, 6),
            Point(-1, 7), Point(0, 7), Point(1, 7), Point(2, 7), Point(3, 7), Point(4, 7), Point(5, 7), Point(6, 7), Point(7, 7), Point(8, 7), Point(9, 7), Point(10, 7), Point(11, 7), Point(12, 7), Point(13, 7), Point(14, 7), Point(15, 7), Point(16, 7), Point(17, 7),
            Point(0, 8), Point(1, 8), Point(2, 8), Point(3, 8), Point(4, 8), Point(5, 8), Point(6, 8), Point(7, 8), Point(8, 8), Point(9, 8), Point(10, 8), Point(11, 8), Point(12, 8), Point(13, 8), Point(14, 8), Point(15, 8), Point(16, 8),
            Point(1, 9), Point(2, 9), Point(3, 9), Point(4, 9), Point(5, 9), Point(6, 9), Point(7, 9), Point(8, 9), Point(9, 9), Point(10, 9), Point(11, 9), Point(12, 9), Point(13, 9), Point(14, 9), Point(15, 9),
            Point(2, 10), Point(3, 10), Point(4, 10), Point(5, 10), Point(6, 10), Point(7, 10), Point(8, 10), Point(9, 10), Point(10, 10), Point(11, 10), Point(12, 10), Point(13, 10), Point(14, 10),
            Point(3, 11), Point(4, 11), Point(5, 11), Point(6, 11), Point(7, 11), Point(8, 11), Point(9, 11), Point(10, 11), Point(11, 11), Point(12, 11), Point(13, 11),
            Point(4, 12), Point(5, 12), Point(6, 12), Point(7, 12), Point(8, 12), Point(9, 12), Point(10, 12), Point(11, 12), Point(12, 12),
            Point(5, 13), Point(6, 13), Point(7, 13), Point(8, 13), Point(9, 13), Point(10, 13), Point(11, 13),
            Point(6, 14), Point(7, 14), Point(8, 14), Point(9, 14), Point(10, 14),
            Point(7, 15), Point(8, 15), Point(9, 15),
            Point(8, 16),
        ).sortedWith(PointComparator())
        assertEquals(expected, result,)
    }

    @Test
    fun testCoverageCount() {
        val result = getCoverageOnLine(10, modelCoverage(parseInput(exampleInput)).also { println(it) })
        assertEquals(26, result)
    }

    companion object {
        val exampleInput = """
            Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            Sensor at x=9, y=16: closest beacon is at x=10, y=16
            Sensor at x=13, y=2: closest beacon is at x=15, y=3
            Sensor at x=12, y=14: closest beacon is at x=10, y=16
            Sensor at x=10, y=20: closest beacon is at x=10, y=16
            Sensor at x=14, y=17: closest beacon is at x=10, y=16
            Sensor at x=8, y=7: closest beacon is at x=2, y=10
            Sensor at x=2, y=0: closest beacon is at x=2, y=10
            Sensor at x=0, y=11: closest beacon is at x=2, y=10
            Sensor at x=20, y=14: closest beacon is at x=25, y=17
            Sensor at x=17, y=20: closest beacon is at x=21, y=22
            Sensor at x=16, y=7: closest beacon is at x=15, y=3
            Sensor at x=14, y=3: closest beacon is at x=15, y=3
            Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimIndent().lines()

        val expectedParsedExampleValues =
            listOf(
                Pair(
                    Point(x = 2, y = 18),
                    Point(x = -2, y = 15)
                ),
                Pair(
                    Point(x = 9, y = 16),
                    Point(x = 10, y = 16)
                ),
                Pair(
                    Point(x = 13, y = 2),
                    Point(x = 15, y = 3)
                ),
                Pair(
                    Point(x = 12, y = 14),
                    Point(x = 10, y = 16)
                ),
                Pair(
                    Point(x = 10, y = 20),
                    Point(x = 10, y = 16)
                ),
                Pair(
                    Point(x = 14, y = 17),
                    Point(x = 10, y = 16)
                ),
                Pair(
                    Point(x = 8, y = 7),
                    Point(x = 2, y = 10)
                ),
                Pair(
                    Point(x = 2, y = 0),
                    Point(x = 2, y = 10)
                ),
                Pair(
                    Point(x = 0, y = 11),
                    Point(x = 2, y = 10)
                ),
                Pair(
                    Point(x = 20, y = 14),
                    Point(x = 25, y = 17)
                ),
                Pair(
                    Point(x = 17, y = 20),
                    Point(x = 21, y = 22)
                ),
                Pair(
                    Point(x = 16, y = 7),
                    Point(x = 15, y = 3)
                ),
                Pair(
                    Point(x = 14, y = 3),
                    Point(x = 15, y = 3)
                ),
                Pair(
                    Point(x = 20, y = 1),
                    Point(x = 15, y = 3)
                ),
            )
    }
}