package com.github.dzkoirn.adventofcode2022.day4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @ParameterizedTest
    @MethodSource("provideIntersectedRangeExamples")
    fun testMapCharacterToScore(testData: Triple<IntRange, IntRange, Boolean>) {
        val (first, second, expected) = testData
        assertEquals(expected, first.fullyIntersectsWith(second))
    }

    @Test
    fun testPuzzle1Example() {
        val result = puzzle1(listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
        ))
        assertEquals(2, result)
    }

    @Test
    fun testPuzzle2Example() {
        val result = puzzle2(listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
        ))
        assertEquals(4, result)
    }

    companion object {
        @JvmStatic
        fun provideIntersectedRangeExamples() =
            listOf(
                Triple(IntRange(2, 8), IntRange(3, 7), true),
                Triple(IntRange(6, 6), IntRange(4, 6), true),
                Triple(IntRange(6, 7), IntRange(4, 6), false),
            )
    }
}