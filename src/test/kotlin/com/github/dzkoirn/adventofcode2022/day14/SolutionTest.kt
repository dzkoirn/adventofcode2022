package com.github.dzkoirn.adventofcode2022.day14

import com.github.dzkoirn.adventofcode2022.Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testParsingInput() {
        val cave = parseInput(exampleInput)
        val printedCave = debugPrintCave(cave)
        assertEquals(exampleParsedCave, printedCave)
    }

    @Test
    fun testSimulationExampleInput() {
        val cave = parseInput(exampleInput)
        val units = runSimulation(cave, Point(500, 0))
        assertEquals(24, units)
    }

    companion object {
        val exampleInput = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent().lines()

        val exampleParsedCave = """
            ..........
            ..........
            ..........
            ..........
            ....#...##
            ....#...#.
            ..###...#.
            ........#.
            ........#.
            #########.
        """.trimIndent()
    }
}