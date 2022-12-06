package com.github.dzkoirn.adventofcode2022.day5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testInputParsing() {
        val result = parseInput(example_input.lines())

        assertEquals(
            expectedStack,
            result.stacks
        )
        assertEquals(expectedCommands, result.commands)
    }

    @Test
    fun testExecuteCommand() {
        val input = parseInput(example_input.lines())
        val result = executeCommands(input)
        assertEquals(expectedResult, result)
    }

    @Test
    fun testPuzzle1() {
        val result = puzzle1(example_input.lines())
        assertEquals(expectedPuzzle1Result, result)
    }

    @Test
    fun testExecuteCommand2() {
        val input = parseInput(example_input.lines())
        val result = executeCommands2(input)
        assertEquals(expectedResult2, result)
    }

    @Test
    fun testPuzzle2() {
        val result = puzzle2(example_input.lines())
        assertEquals(expectedPuzzle2Result, result)
    }

    companion object {

        val example_input = """
           |    [D]
           |[N] [C]
           |[Z] [M] [P]
           | 1   2   3
           |
           |move 1 from 2 to 1
           |move 3 from 1 to 3
           |move 2 from 2 to 1
           |move 1 from 1 to 2
        """.trimMargin("|")

        val expectedStack = listOf(
            listOf('Z', 'N'),
            listOf('M', 'C', 'D'),
            listOf('P')
        )

        val expectedCommands = listOf(
            Input.Command(1, 2, 1),
            Input.Command(3, 1, 3),
            Input.Command(2, 2, 1),
            Input.Command(1, 1, 2)
        )

        /**
         *         [Z]
         *         [N]
         *         [D]
         * [C] [M] [P]
         *  1   2   3
         */
        val expectedResult =
            listOf(
                listOf('C'),
                listOf('M'),
                listOf('P', 'D', 'N', 'Z')
            )

        val expectedPuzzle1Result = "CMZ"

        /**
         *         [D]
         *         [N]
         *         [Z]
         * [M] [C] [P]
         *  1   2   3
         */
        val expectedResult2 =
            listOf(
                listOf('M'),
                listOf('C'),
                listOf('P', 'Z', 'N', 'D')
            )

        val expectedPuzzle2Result = "MCD"
    }
}
