package com.github.dzkoirn.adventofcode2022.day9

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @ParameterizedTest
    @MethodSource("moveExample")
    fun moveRopeTest(testCase: MoveTestCase) {
        val result = moveRope(testCase.startRope, testCase.direction)
        assertEquals(testCase.expectedRope, result)
    }

    @Test
    fun test2KnotsOnSmallExampleposition() {
        val result = executeCommands(
            start = Pair(0, 0),
            size = 2,
            commands = parseInput(smallExampleInputCommands.lines())
        )
        assertEquals(expectedPositionOf2KnotRopeAfterSmallExampleInput, result)
    }

    @Test
    fun test2KnotsOnSmallExampleTailVisitedPoints() {
        val result = countTailVisitedPoints(
            lines = smallExampleInputCommands.lines(),
            start = Pair(0,0),
            knotSize = 2
        )
        assertEquals(expected2KnotTailVisitedPointsOnSmallInput, result)
    }

    @Test
    fun test10KnotsOnSmallExamplePosition() {
        val result = executeCommands(
            start = Pair(0, 0),
            size = 10,
            commands = parseInput(smallExampleInputCommands.lines())
        )
        assertEquals(expectedpostionOf10KnotRopeAfterSmallExampleInput, result)
    }

    @Test
    fun test10KnotsOnLargeExampleposition() {
        val result = executeCommands(
            start = Pair(11, 5),
            size = 10,
            commands = parseInput(large10KnotsExampleCommands.lines())
        )
        assertEquals(expected10KnotRopeAfterLargeExampleInput, result)
    }

    @Test
    fun test10knotOnSmallExamplePointsVisited() {
        val result = countTailVisitedPoints(
            lines = smallExampleInputCommands.lines(),
            start = Pair(0,0),
            knotSize = 10
        )
        assertEquals(expected10KnotTailVisitedPointsOnSmallExample, result)
    }

    @Test
    fun test10KnotsOnLargeExamplePointsVisited() {
        val result = countTailVisitedPoints(
            lines = large10KnotsExampleCommands.lines(),
            start = Pair(11,5),
            knotSize = 10
        )
        assertEquals(expected10KnotTailVisitedPointsOnLargeExample, result)
    }

    companion object {

        data class MoveTestCase(
            val startRope: List<Knot>,
            val direction: Direction,
            val expectedRope: List<Knot>
        )

        @JvmStatic
        fun moveExample() = listOf(
            /*
             * .....    .....    .....
             * .TH.. -> .T.H. -> ..TH.
             * .....    .....    .....
             */
            MoveTestCase(
                startRope = listOf(Pair(3, 1), Pair(2, 1)),
                direction = Direction.RIGHT,
                expectedRope = listOf(Pair(4, 1), Pair(3, 1)),
            ),
            /*
             * ...    ...    ...
             * .T.    .T.    ...
             * .H. -> ... -> .T.
             * ...    .H.    .H.
             * ...    ...    ...
             */
            MoveTestCase(
                startRope = listOf(Pair(1, 2), Pair(1, 3)),
                direction = Direction.DOWN,
                expectedRope = listOf(Pair(1, 1), Pair(1, 2)),
            ),
            /*
             *  .....    .....    .....
             *  .....    ..H..    ..H..
             *  ..H.. -> ..... -> ..T..
             *  .T...    .T...    .....
             *  .....    .....    .....
             */
            MoveTestCase(
                startRope = listOf(Pair(2, 2), Pair(1, 1)),
                direction = Direction.UP,
                expectedRope = listOf(Pair(2, 3), Pair(2, 2)),
            ),
             /*
              *  .....    .....    .....
              *  .....    .....    .....
              *  ..H.. -> ...H. -> ..TH.
              *  .T...    .T...    .....
              *  .....    .....    .....
              */
            MoveTestCase(
                startRope = listOf(Pair(2, 2), Pair(1, 1)),
                direction = Direction.RIGHT,
                expectedRope = listOf(Pair(3, 2), Pair(2, 2)),
            ),
        )

        val smallExampleInputCommands = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent()

        val expectedPositionOf2KnotRopeAfterSmallExampleInput =
            listOf(Pair(2, 2), Pair(1, 2))

        const val expected2KnotTailVisitedPointsOnSmallInput = 13

        /*
         * ......
         * ......
         * .1H3..  (H covers 2, 4)
         * .5....
         * 6.....  (6 covers 7, 8, 9, s)
         */
        val expectedpostionOf10KnotRopeAfterSmallExampleInput =
            listOf(Pair(2, 2), Pair(1, 2), Pair(2, 2), Pair(3, 2), Pair(2, 2),
                Pair(1, 1), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))

        val large10KnotsExampleCommands = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent()

        /*
         *   H.........................
         *   1.........................
         *   2.........................
         *   3.........................
         *   4.........................
         *   5.........................
         *   6.........................
         *   7.........................
         *   8.........................
         *   9.........................
         *   ..........................
         *   ..........................
         *   ..........................
         *   ..........................
         *   ..........................
         *   ...........s..............
         *   ..........................
         *   ..........................
         *   ..........................
         *   ..........................
         *   ..........................
         */
        val expected10KnotRopeAfterLargeExampleInput =
            listOf(Pair(0, 20), Pair(0, 19), Pair(0, 18), Pair(0, 17), Pair(0, 16),
                Pair(0, 15), Pair(0, 14), Pair(0, 13), Pair(0, 12), Pair(0, 11))

        const val expected10KnotTailVisitedPointsOnSmallExample = 1
        const val expected10KnotTailVisitedPointsOnLargeExample = 36
    }
}