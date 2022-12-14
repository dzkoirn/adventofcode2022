package com.github.dzkoirn.adventofcode2022.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testSmallInputExample() {
        val collector = EverythingCollector()
        val endValue = execute(parseInput(smallInputExample.lines()), collector::onTick)
        assertEquals(smallInputExpectedOutput, collector.getValues())
        assertEquals(-1, endValue)
    }

    @Test
    fun testValuesOnTicks() {
        val result = getValuesOnSpecificTicks(exampleInput.lines(), ticksToCare)
            .map { (_, value) -> value }
        assertEquals(expectedValuesOnTicks, result)
    }

    @Test
    fun testSignalStrengthCalculation() {
        val result = getValuesOnSpecificTicks(exampleInput.lines(), ticksToCare)
            .calculateSignalStrength()
        assertEquals(expectedSignalStrength, result)
    }

    @Test
    fun testImageDrawing() {
        val collector = CrtCollector()
        execute(parseInput(exampleInput.lines()), collector::onTick)

        println("Expected")
        println(expectedImage)

        println("Actual")
        println(collector.getText())

        assertEquals(expectedImage, collector.getText())
    }

    companion object {
        val smallInputExample = """
            noop
            addx 3
            addx -5
        """.trimIndent()

        val smallInputExpectedOutput =
            listOf(
                Pair(1L, 1),
                Pair(2L, 1),
                Pair(3L, 1),
                Pair(4L, 4),
                Pair(5L, 4),
            )

        val exampleInput = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx -35
            addx 1
            addx 24
            addx -19
            addx 1
            addx 16
            addx -11
            noop
            noop
            addx 21
            addx -15
            noop
            noop
            addx -3
            addx 9
            addx 1
            addx -3
            addx 8
            addx 1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx -36
            noop
            addx 1
            addx 7
            noop
            noop
            noop
            addx 2
            addx 6
            noop
            noop
            noop
            noop
            noop
            addx 1
            noop
            noop
            addx 7
            addx 1
            noop
            addx -13
            addx 13
            addx 7
            noop
            addx 1
            addx -33
            noop
            noop
            noop
            addx 2
            noop
            noop
            noop
            addx 8
            noop
            addx -1
            addx 2
            addx 1
            noop
            addx 17
            addx -9
            addx 1
            addx 1
            addx -3
            addx 11
            noop
            noop
            addx 1
            noop
            addx 1
            noop
            noop
            addx -13
            addx -19
            addx 1
            addx 3
            addx 26
            addx -30
            addx 12
            addx -1
            addx 3
            addx 1
            noop
            noop
            noop
            addx -9
            addx 18
            addx 1
            addx 2
            noop
            noop
            addx 9
            noop
            noop
            noop
            addx -1
            addx 2
            addx -37
            addx 1
            addx 3
            noop
            addx 15
            addx -21
            addx 22
            addx -6
            addx 1
            noop
            addx 2
            addx 1
            noop
            addx -10
            noop
            noop
            addx 20
            addx 1
            addx 2
            addx 2
            addx -6
            addx -11
            noop
            noop
            noop
        """.trimIndent()

        val ticksToCare = arrayOf(20L, 60L, 100L, 140L, 180L, 220L)
        val expectedValuesOnTicks = listOf(21, 19, 18, 21, 16, 18)
        const val expectedSignalStrength = 13140L

        val expectedImage = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
    }
}