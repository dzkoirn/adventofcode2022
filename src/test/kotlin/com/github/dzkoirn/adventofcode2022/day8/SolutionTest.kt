package com.github.dzkoirn.adventofcode2022.day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testParseInput() {
        val result = parseInput(exampleInput.lines())
        assertEquals(expectedExampleMatrix, result)
    }
    @Test
    fun testPuzzle1OnTestInput() {
        val result = puzzle1(exampleInput.lines())
        assertEquals(expectedPuzzle1Result, result)
    }

    @Test
    fun testPuzzle2OnTestInput() {
        val result = puzzle2(exampleInput.lines())
        assertEquals(expectedPuzzle2Result, result)
    }

    companion object {
        val exampleInput = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        val expectedExampleMatrix =
            listOf(
                listOf(3, 0, 3, 7, 3),
                listOf(2, 5, 5, 1, 2),
                listOf(6, 5, 3, 3, 2),
                listOf(3, 3, 5, 4, 9),
                listOf(3, 5, 3, 9, 0),
            )

        const val expectedPuzzle1Result = 21
        const val expectedPuzzle2Result = 8
    }
}