package com.github.dzkoirn.adventofcode2022.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testFindPath() {
        val steps = findPathAndCountSteps(exampleInput.lines())
        assertEquals(31, steps)
    }

    companion object {
        val exampleInput = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()
    }
}