@file:Suppress("UNCHECKED_CAST")

package com.github.dzkoirn.adventofcode2022.day13

import com.github.dzkoirn.adventofcode2022.readInput
import org.json.JSONArray
import org.json.JSONException
import java.lang.Integer.min

fun main() {
    val input = readInput("day_13_input")
    println("Start")
    println("Puzzle 1")
    println(puzzle1(input))
    println("===================")
    println("Puzzle 2")
    println(puzzle2(input))
    println("Finish")
}

fun parseInputToPair(input: List<String>): List<Pair<List<Any>, List<Any>>> {
    return input.windowed(2, step = 3).map { (left, right) ->
        try {
            Pair(
                first = JSONArray(left).toList(),
                second = JSONArray(right).toList()
            )
        } catch (ex:JSONException) {
            throw RuntimeException("\n Left = {$left}\n Right = {$right}", ex)
        }
    }
}

fun puzzle1(input: List<String>): Int {
    return parseInputToPair(input)
        .map { (left, right) -> compareValues(left, right) }
        .mapIndexed { index, b ->
            if (b < 0) {
                index + 1
            } else {
                0
            }
        }.sum()

}

fun compareValues(left: Any, right: Any): Int {
    return when {
        left is List<*> || right is List<*> -> {
            val l = left.asList()
            val r = right.asList()
            for (i in 0 until min(l.size, r.size)) {
                val result = compareValues(l[i], r[i])
                if (result != 0) {
                    return result
                }
            }
            l.size.compareTo(r.size)
        }
        else -> left.asInt().compareTo(right.asInt())
    }
}

fun Any.asList(): List<Any> =
    if (this is List<*>) {
        this as List<Any>
    } else {
        listOf(this)
    }

fun Any.asInt(): Int = (this as Number).toInt()

val dividerPackets: List<Any>
    get() = parseInputToLists("""
        [[2]]
        [[6]]
    """.trimIndent().lines())

fun parseInputToLists(input: List<String>): List<List<Any>> =
    input.filterNot { it.isBlank() or it.isEmpty() }
        .map {
            try {
                JSONArray(it).toList()
            } catch (ex:JSONException) {
                throw RuntimeException(it, ex)
            }
        }

fun puzzle2(input: List<String>): Int {
    val sorted = (parseInputToLists(input) + dividerPackets).sortedWith(::compareValues)
    return dividerPackets.map { sorted.indexOf(it) + 1 }
        .onEach { println("Debug: index $it") }
        .reduce { acc, i -> acc * i}
}