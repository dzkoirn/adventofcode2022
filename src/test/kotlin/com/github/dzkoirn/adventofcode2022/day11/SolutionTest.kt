package com.github.dzkoirn.adventofcode2022.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import kotlin.collections.List

class SolutionTest {

    @Test
    fun testIndexAfterExtension() {
        val index = "    If true: throw to monkey 1".indexAfter("If true: throw to monkey ")
        assertEquals(29, index)
    }

    @ParameterizedTest
    @MethodSource("provideOperationsTestCases")
    fun testOperationParsing(testCase: Pair<String, Operation>) {
        val actualOperation = Operation.parseString(testCase.first)
        assertEquals(testCase.second, actualOperation)
    }

    @Test
    fun testParseInput() {
        val result = parseInput(exampleInput.lines())
        assertEquals(expectedParsedInput, result)
    }

    @Test
    fun testFirstRound() {
        val monkeysStart = parseInput(exampleInput.lines())
        playRoundV1(monkeysStart,)

        assertEquals(listOf(20L, 23L, 27L, 26L), monkeysStart[0].items)
        assertEquals(listOf(2080L, 25L, 167L, 207L, 401L, 1046L), monkeysStart[1].items)
        assertTrue(monkeysStart[2].items.isEmpty())
        assertTrue(monkeysStart[3].items.isEmpty())
    }

    @Test
    fun test20Rounds() {
        val monkeysStart = parseInput(exampleInput.lines())

        repeat(20) {
            playRoundV1(monkeysStart)
        }

        assertEquals(listOf(10L, 12L, 14L, 26L, 34L), monkeysStart[0].items)
        assertEquals(listOf(245L, 93L, 53L, 199L, 115L), monkeysStart[1].items)
        assertTrue(monkeysStart[2].items.isEmpty())
        assertTrue(monkeysStart[3].items.isEmpty())
    }

    @Test
    fun test20RoundsCountExpections() {
        val monkeysStart = parseInput(exampleInput.lines())
        val counter = Counter()

        repeat(20) {
            playRoundV1(monkeysStart, counter::onInspection)
        }

        assertEquals(101, counter.counter[0])
        assertEquals(95, counter.counter[1])
        assertEquals(7, counter.counter[2])
        assertEquals(105, counter.counter[3])

        assertEquals(10605, counter.calculateMonkeyBusines())
    }

    @Test
    fun testFirstRoundNoDivider() {
        val counter = Counter()
        val monkeysStart = parseInput(exampleInput.lines())
        playRoundV2(monkeysStart, counter::onInspection)

        assertEquals(2, counter.counter[0])
        assertEquals(4, counter.counter[1])
        assertEquals(3, counter.counter[2])
        assertEquals(6, counter.counter[3])
    }

    @Test
    fun test20RoundsCountExpectionsNoDivider() {
        val monkeysStart = parseInput(exampleInput.lines())

        val counter = Counter()

        repeat(20) {
            playRoundV2(monkeysStart, counter::onInspection)
        }

        assertEquals(99, counter.counter[0])
        assertEquals(97, counter.counter[1])
        assertEquals(8, counter.counter[2])
        assertEquals(103, counter.counter[3])
    }

    @Test
    fun test10000RoundsCountExpectionsNewDivider() {
        val monkeysStart = parseInput(exampleInput.lines())
        val counter = Counter()

        repeat(10000) {
            playRoundV2(monkeysStart, counter::onInspection)
        }

        assertEquals(52166, counter.counter[0])
        assertEquals(47830, counter.counter[1])
        assertEquals(1938, counter.counter[2])
        assertEquals(52013, counter.counter[3])

        assertEquals(2713310158, counter.calculateMonkeyBusines())
    }

    companion object {

        @JvmStatic
        fun provideOperationsTestCases() =
            listOf(
                Pair(
                    first = "Operation: new = old * 19",
                    second = Operation(
                        leftOperand = Operation.Companion.Operand.OldOperand,
                        arithmeticOperation = Operation.Companion.ArithmeticOperation.MUL,
                        rightOperand = Operation.Companion.Operand.ConstOperand(19)
                    )
                ),
                Pair(
                    first = "Operation: new = old + old",
                    second = Operation(
                        leftOperand = Operation.Companion.Operand.OldOperand,
                        arithmeticOperation = Operation.Companion.ArithmeticOperation.PLUS,
                        rightOperand = Operation.Companion.Operand.OldOperand
                    )
                ),
                Pair(
                    first = "Operation: new = old - 5",
                    second = Operation(
                        leftOperand = Operation.Companion.Operand.OldOperand,
                        arithmeticOperation = Operation.Companion.ArithmeticOperation.MINUS,
                        rightOperand = Operation.Companion.Operand.ConstOperand(5)
                    )
                ),
                Pair(
                    first = "Operation: new = 7 / old",
                    second = Operation(
                        leftOperand = Operation.Companion.Operand.ConstOperand(7),
                        arithmeticOperation = Operation.Companion.ArithmeticOperation.DIV,
                        rightOperand = Operation.Companion.Operand.OldOperand
                    )
                ),
            )

        val exampleInput = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
            
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()

        internal val expectedParsedInput = listOf(
            Monkey(
                id = 0,
                items = listOf(79L, 98L).toLinkedList(),
                operation = Operation(
                    leftOperand = Operation.Companion.Operand.OldOperand,
                    arithmeticOperation = Operation.Companion.ArithmeticOperation.MUL,
                    rightOperand = Operation.Companion.Operand.ConstOperand(19)
                ),
                test = 23,
                nextMonkeyIds = NextMonkeyIds(2, 3),
            ),
            Monkey(
                id = 1,
                items = listOf(54L, 65L, 75L, 74L).toLinkedList(),
                operation = Operation(
                    leftOperand = Operation.Companion.Operand.OldOperand,
                    arithmeticOperation = Operation.Companion.ArithmeticOperation.PLUS,
                    rightOperand = Operation.Companion.Operand.ConstOperand(6)
                ),
                test = 19,
                nextMonkeyIds = NextMonkeyIds(2, 0),
            ),
            Monkey(
                id = 2,
                items = listOf(79L, 60L, 97L).toLinkedList(),
                operation = Operation(
                    leftOperand = Operation.Companion.Operand.OldOperand,
                    arithmeticOperation = Operation.Companion.ArithmeticOperation.MUL,
                    rightOperand = Operation.Companion.Operand.OldOperand
                ),
                test = 13,
                nextMonkeyIds = NextMonkeyIds(1, 3),
            ),
            Monkey(
                id = 3,
                items = listOf(74L).toLinkedList(),
                operation = Operation(
                    leftOperand = Operation.Companion.Operand.OldOperand,
                    arithmeticOperation = Operation.Companion.ArithmeticOperation.PLUS,
                    rightOperand = Operation.Companion.Operand.ConstOperand(3)
                ),
                test = 17,
                nextMonkeyIds = NextMonkeyIds(0, 1),
            ),
        )

        private fun <E> List<E>.toLinkedList(): LinkedList<E> =
            LinkedList(this)
    }
}


