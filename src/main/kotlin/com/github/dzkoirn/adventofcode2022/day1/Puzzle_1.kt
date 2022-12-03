package com.github.dzkoirn.adventofcode2022.day1

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

fun main(args: Array<String>) {
    val elvesCaloriesList = getElvesCaloriesList()

    println("Start")
    puzzle1(elvesCaloriesList)
    println("---------------")
    puzzle2(elvesCaloriesList)
    println("Finish")
}

private fun getElvesCaloriesList(): List<Int> =
    object {}.javaClass.classLoader.getResourceAsStream("day_1_input")
        .let { BufferedInputStream(it) }
        .use { input ->
            BufferedReader(InputStreamReader(input))
                .lines()
                .asSequence()
                .map { it.toIntOrNull() }
                .fold(mutableListOf(0)) { acc, i ->
                    acc.also {
                        if (i != null) {
                            acc[acc.lastIndex] = acc.last() + i
                        } else {
                            acc.add(0)
                        }
                    }
                }
        }

private fun puzzle1(elvesCalories: List<Int>) {
    println("Puzzle 1")
    println(elvesCalories.max())
}

private fun puzzle2(elvesCalories: List<Int>) {
    println("Puzzle 2")
    elvesCalories.sortedDescending().take(3).sum().let { println(it) }
}