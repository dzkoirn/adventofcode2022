package com.github.dzkoirn.adventofcode2022.day3

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PuzzleTest {

    @ParameterizedTest
    @MethodSource("provideTestDataCharacterMapping")
    fun testMapCharacterToScore(testData: TestData) {
        val actual = mapCharacterToScore(testData.input)
        Assertions.assertEquals(
            testData.expected,
            actual,
            "mapCharacterToScore for $testData was $actual"
        )
    }

    @ParameterizedTest
    @MethodSource("provideTestDataCharacterMappingExample")
    fun testMapCharacterToScoreOnExamples(testData: TestData) {
        val actual = mapCharacterToScore(testData.input)
        Assertions.assertEquals(
            testData.expected,
            actual,
            "mapCharacterToScore for $testData was $actual"
        )
    }

    @ParameterizedTest
    @MethodSource("provideTestDataDivisionExample")
    fun testStringDivision(testData: Triple<String, String, String>) {
        Assertions.assertEquals(
            Pair(testData.second,testData.third),
            testData.first.divide()
        )
    }

    @Test
    fun testRucksaksComparesment() {
        val result = findDuplicatesItems(provideTestDataPuzzle1ExampleInput())
        Assertions.assertArrayEquals(
            exampleInputComparementResult,
            result.toTypedArray().toCharArray()
        )
    }

    @Test
    fun testPuzzle1() {
        Assertions.assertEquals(157, puzzle1(provideTestDataPuzzle1ExampleInput()))
    }

    @ParameterizedTest
    @MethodSource("provideTestDataIntersectionExample")
    fun testIntersectionExample(testData: Pair<List<String>, Char>) {
        Assertions.assertEquals(
            testData.second,
            findIntersection(testData.first)
        )
    }

    @Test
    fun testPuzzle2() {
        Assertions.assertEquals(70, puzzle2(provideTestDataPuzzle2ExampleInput()))
    }

    companion object {
        data class TestData(
            val input: Char,
            val expected: Int
        )

        @JvmStatic
        fun provideTestDataCharacterMapping() =
            (('a'..'z') + ('A'..'Z'))
                .mapIndexed { index, c -> TestData(c, index + 1) }
                .toList()

        @JvmStatic
        fun provideTestDataCharacterMappingExample() =
            listOf(
                TestData('p', 16),
                TestData('L', 38),
                TestData('P', 42),
                TestData('v', 22),
                TestData('t', 20),
                TestData('s', 19)
            )

        @JvmStatic
        fun provideTestDataDivisionExample() =
            listOf(
                Triple("vJrwpWtwJgWrhcsFMMfFFhFp", "vJrwpWtwJgWr", "hcsFMMfFFhFp"),
                Triple("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL"),
                Triple("PmmdzqPrVvPwwTWBwg", "PmmdzqPrV", "vPwwTWBwg"),
            )

        @JvmStatic
        fun provideTestDataPuzzle1ExampleInput() =
            listOf(
               "vJrwpWtwJgWrhcsFMMfFFhFp",
               "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
               "PmmdzqPrVvPwwTWBwg",
               "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
               "ttgJtRGJQctTZtZT",
               "CrZsJsPPZsGzwwsLwLmpwMDw"
            )

        @JvmStatic
        fun provideTestDataIntersectionExample(): Iterable<Pair<List<String>, Char>> =
            listOf(
                Pair(
                    listOf(
                        "vJrwpWtwJgWrhcsFMMfFFhFp",
                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                        "PmmdzqPrVvPwwTWBwg"
                    ), 'r'
                ),
                Pair(
                    listOf(
                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                        "ttgJtRGJQctTZtZT",
                        "CrZsJsPPZsGzwwsLwLmpwMDw"
                    ), 'Z'
                )
            )

        // 16 (p), 38 (L), 42 (P), 22 (v), 20 (t), and 19 (s)

        val exampleInputComparementResult =
            charArrayOf('p', 'L', 'P', 'v', 't', 's')

        @JvmStatic
        fun provideTestDataPuzzle2ExampleInput() =
            listOf(
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
            )

    }

}