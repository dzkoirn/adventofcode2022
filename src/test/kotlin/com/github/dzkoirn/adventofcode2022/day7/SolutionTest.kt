package com.github.dzkoirn.adventofcode2022.day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun testParsing() {
        val result = parseInput(exampleInput)
        assertEquals(expectedTree, result)
    }

    @Test
    fun testCountDirSize() {
        val result = parseInput(exampleInput).countDirSize()
        assertEquals(48381165L, result)
    }

    @Test
    fun testPuzzle1OnTestData() {
        val result = puzzle1(exampleInput)
        assertEquals(95437L, result)
    }

    @Test
    fun testPuzzle2OnTestData() {
        val result = puzzle2(exampleInput)
        assertEquals(24933642L, result)
    }

    companion object {
        val exampleInput = """
            ${'$'} cd /
            ${'$'} ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            ${'$'} cd a
            ${'$'} ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            ${'$'} cd e
            ${'$'} ls
            584 i
            ${'$'} cd ..
            ${'$'} cd ..
            ${'$'} cd d
            ${'$'} ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
        """.trimIndent().lines()

        /**
         * - / (dir)
         *         - a (dir)
         *             - e (dir)
         *                 - i (file, size=584)
         *             - f (file, size=29116)
         *             - g (file, size=2557)
         *             - h.lst (file, size=62596)
         *         - b.txt (file, size=14848514)
         *         - c.dat (file, size=8504156)
         *         - d (dir)
         *             - j (file, size=4060174)
         *             - d.log (file, size=8033020)
         *             - d.ext (file, size=5626152)
         *             - k (file, size=7214296)
         */
        val expectedTree =
            Item.Dir(
                name = "/",
                items = mutableListOf(
                    Item.Dir(
                        name = "a",
                        items = mutableListOf(
                            Item.Dir(
                                name = "e",
                                items = mutableListOf(
                                    Item.File(
                                        name = "i",
                                        size = 584
                                    )
                                )
                            ),
                            Item.File(name = "f", size = 29116),
                            Item.File(name = "g", size = 2557),
                            Item.File(name = "h.lst", size = 62596),
                        )
                    ),
                    Item.File(name = "b.txt", size = 14848514),
                    Item.File(name = "c.dat", size = 8504156),
                    Item.Dir(
                        name = "d",
                        items = mutableListOf(
                            Item.File(name = "j", size = 4060174),
                            Item.File(name = "d.log", size = 8033020),
                            Item.File(name = "d.ext", size = 5626152),
                            Item.File(name = "k", size = 7214296),
                        )
                    )
                )
            )
    }
}