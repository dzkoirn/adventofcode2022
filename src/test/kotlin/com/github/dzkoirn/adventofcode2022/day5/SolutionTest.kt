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
    }
}
