package com.github.dzkoirn.adventofcode2022.day6

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @ParameterizedTest
    @MethodSource("providePuzzle1ExampleInput")
    fun testPuzzle1(testData: Pair<String, Int>) {
        val (testInput, expected) = testData
        val actual = puzzle1(testInput)
        Assertions.assertEquals(
            expected,
            actual
        )
    }

    @ParameterizedTest
    @MethodSource("providePuzzle2ExampleInput")
    fun testPuzzle2(testData: Pair<String, Int>) {
        val (testInput, expected) = testData
        val actual = puzzle2(testInput)
        Assertions.assertEquals(
            expected,
            actual
        )
    }


    companion object {

        @JvmStatic
        fun providePuzzle1ExampleInput() =
            listOf(
                "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7,
                "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
                "nppdvjthqldpwncqszvftbrmjlhg" to 6,
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
            )

        @JvmStatic
        fun providePuzzle2ExampleInput() =
            listOf(
                "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
                "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
                "nppdvjthqldpwncqszvftbrmjlhg" to 23,
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
            )
    }
}