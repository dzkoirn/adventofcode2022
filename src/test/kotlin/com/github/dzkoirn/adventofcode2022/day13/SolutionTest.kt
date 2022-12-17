package com.github.dzkoirn.adventofcode2022.day13

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    fun testisOrderCorrect(testCase: TestCase) {
        val result = compareValues(testCase.left, testCase.right)
        assertEquals(testCase.expected, result == -1)
    }

    @Test
    fun testPuzzle1ExampleInput() {
        val result = puzzle1(exampleInput.lines())
        assertEquals(13, result)
    }

    companion object {
        val exampleInput = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent()

        data class TestCase(
            val left: List<Any>,
            val right: List<Any>,
            val expected: Boolean
        )

        @JvmStatic
        fun provideTestCases() =
            listOf(
                /*
                    == Pair 1 ==
                    - Compare [1,1,3,1,1] vs [1,1,5,1,1]
                      - Compare 1 vs 1
                      - Compare 1 vs 1
                      - Compare 3 vs 5
                        - Left side is smaller, so inputs are in the right order
                */
                TestCase(
                    left = JSONArray("[1,1,3,1,1]").toList(),
                    right = JSONArray("[1,1,5,1,1]").toList(),
                    expected = true
                ),
                /*
                    == Pair 2 ==
                    - Compare [[1],[2,3,4]] vs [[1],4]
                      - Compare [1] vs [1]
                        - Compare 1 vs 1
                      - Compare [2,3,4] vs 4
                        - Mixed types; convert right to [4] and retry comparison
                        - Compare [2,3,4] vs [4]
                          - Compare 2 vs 4
                            - Left side is smaller, so inputs are in the right order
                */
                TestCase(
                    left = JSONArray("[[1],[2,3,4]]").toList(),
                    right = JSONArray("[[1],4]").toList(),
                    expected = true
                ),
                /*  == Pair 3 ==
                    - Compare [9] vs [[8,7,6]]
                      - Compare 9 vs [8,7,6]
                        - Mixed types; convert left to [9] and retry comparison
                        - Compare [9] vs [8,7,6]
                          - Compare 9 vs 8
                            - Right side is smaller, so inputs are not in the right order
                 */
                TestCase(
                    left = JSONArray("[9]").toList(),
                    right = JSONArray("[[8,7,6]]").toList(),
                    expected = false
                ),
                /* == Pair 4 ==
                    - Compare [[4,4],4,4] vs [[4,4],4,4,4]
                      - Compare [4,4] vs [4,4]
                        - Compare 4 vs 4
                        - Compare 4 vs 4
                      - Compare 4 vs 4
                      - Compare 4 vs 4
                      - Left side ran out of items, so inputs are in the right order
                */
                TestCase(
                    left = JSONArray("[[4,4],4,4]").toList(),
                    right = JSONArray("[[4,4],4,4,4]").toList(),
                    expected = true
                ),
                /* == Pair 5 ==
                    - Compare [7,7,7,7] vs [7,7,7]
                      - Compare 7 vs 7
                      - Compare 7 vs 7
                      - Compare 7 vs 7
                      - Right side ran out of items, so inputs are not in the right order
                */
                TestCase(
                    left = JSONArray("[7,7,7,7]").toList(),
                    right = JSONArray("[7,7,7]").toList(),
                    expected = false
                ),
                 /* == Pair 6 ==
                    - Compare [] vs [3]
                      - Left side ran out of items, so inputs are in the right order
                 */
                TestCase(
                    left = JSONArray("[]").toList(),
                    right = JSONArray("[3]").toList(),
                    expected = true
                ),
                 /* == Pair 7 ==
                    - Compare [[[]]] vs [[]]
                      - Compare [[]] vs []
                        - Right side ran out of items, so inputs are not in the right order
                 */
                TestCase(
                    left = JSONArray("[[[]]]").toList(),
                    right = JSONArray("[[]]").toList(),
                    expected = false
                ),
                 /* == Pair 8 ==
                    - Compare [1,[2,[3,[4,[5,6,7]]]],8,9] vs [1,[2,[3,[4,[5,6,0]]]],8,9]
                      - Compare 1 vs 1
                      - Compare [2,[3,[4,[5,6,7]]]] vs [2,[3,[4,[5,6,0]]]]
                        - Compare 2 vs 2
                        - Compare [3,[4,[5,6,7]]] vs [3,[4,[5,6,0]]]
                          - Compare 3 vs 3
                          - Compare [4,[5,6,7]] vs [4,[5,6,0]]
                            - Compare 4 vs 4
                            - Compare [5,6,7] vs [5,6,0]
                              - Compare 5 vs 5
                              - Compare 6 vs 6
                              - Compare 7 vs 0
                                - Right side is smaller, so inputs are not in the right order
                 */
                TestCase(
                    left = JSONArray("[1,[2,[3,[4,[5,6,7]]]],8,9]").toList(),
                    right = JSONArray("[1,[2,[3,[4,[5,6,0]]]],8,9]").toList(),
                    expected = false
                ),
            )
    }
}