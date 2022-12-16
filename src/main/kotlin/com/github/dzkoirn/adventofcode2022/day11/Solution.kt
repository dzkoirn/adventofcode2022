package com.github.dzkoirn.adventofcode2022.day11

import com.github.dzkoirn.adventofcode2022.readInput
import java.util.LinkedList

fun main() {
    val input = readInput("day_11_input")
    println("Start")
    println("Puzzle 1")
    println(puzzle1(input))
    println("===================")
    println("Puzzle 2")
    println(puzzle2(input))
    println("Finish")
}
data class Monkey(
    val id: Int,
    val items: LinkedList<Long>,
    val operation: Operation,
    val test: Int,
    val nextMonkeyIds: NextMonkeyIds,
)

typealias NextMonkeyIds = Pair<Int, Int>

val NextMonkeyIds.trueMonkeyId: Int
    get() = this.first

val NextMonkeyIds.falseMonkeyId: Int
    get() = this.second

internal fun parseInput(input:List<String>): List<Monkey> =
    input.windowed(size = 6, step = 7).map { m -> stupidMonkeyDestruction(m) }


internal fun stupidMonkeyDestruction(m: List<String>): Monkey {
    /* Monkey 0:
        Starting items : 79, 98
        Operation: new = old * 19
        Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
    */
    return Monkey(
        id = m[0].let { idString ->
            idString.substring("Monkey ".length, idString.indexOf(':')).toInt()
        },
        items = m[1].let { itemsString -> itemsString.split(':').last().split(",")
            .map { it.trim().toInt().toLong() }}.let { LinkedList(it) },
        operation = Operation.parseString(m[2]),
        test = m[3].let { it.substring(it.indexAfter("Test: divisible by ")).toInt() },
        nextMonkeyIds = NextMonkeyIds(
            first = m[4].let { it.substring(it.indexAfter("If true: throw to monkey ")).toInt() },
            second = m[5].let { it.substring(it.indexAfter("If false: throw to monkey ")).toInt() },
        )
    )
}

internal fun CharSequence.indexAfter(string: String): Int =
    indexOf(string) + string.length


fun playRoundV1(
    list: List<Monkey>,
    onInspection: (id: Int) -> Unit = { }
) {
    list.forEach { monkey ->
        while (!monkey.items.isEmpty()) {
            val worryLevel = monkey.items.poll()
            onInspection(monkey.id)
            val stressLevel = monkey.operation.invoke(worryLevel)
            val newStressLevel = stressLevel / 3
            if (newStressLevel % monkey.test == 0L) {
                list[monkey.nextMonkeyIds.trueMonkeyId].items.add(newStressLevel)
            } else {
                list[monkey.nextMonkeyIds.falseMonkeyId].items.add(newStressLevel)
            }
        }
    }
}

class Counter {
    val counter = mutableMapOf<Int, Long>()

    fun onInspection(id: Int) {
        counter[id] = counter.getOrDefault(id, 0L) + 1
    }

    fun calculateMonkeyBusines() =
        counter.values.sortedDescending().take(2).fold(1L) { acc, entry -> acc * entry }
}

fun puzzle1(input: List<String>): Long {
    val monkeysStart = parseInput(input)
    val counter = Counter()

    repeat(20) {
        playRoundV1(monkeysStart, counter::onInspection)
    }
    return counter.calculateMonkeyBusines()
}

fun playRoundV2(
    list: List<Monkey>,
    onInspection: (id: Int) -> Unit = { }
) {
    val commonMultiplier = list.map { it.test }.fold(1L) { acc, v -> acc * v}
    list.forEach { monkey ->
        while (!monkey.items.isEmpty()) {
            val worryLevel = monkey.items.poll()
            onInspection(monkey.id)
            val stressLevel = monkey.operation.invoke(worryLevel)
            val newStressLevel = stressLevel % commonMultiplier
            if (newStressLevel % monkey.test == 0L) {
                list[monkey.nextMonkeyIds.trueMonkeyId].items.add(newStressLevel)
            } else {
                list[monkey.nextMonkeyIds.falseMonkeyId].items.add(newStressLevel)
            }
        }
    }
}

fun puzzle2(input: List<String>): Long {
    val monkeysStart = parseInput(input)
    val counter = Counter()

    repeat(10000) {
        playRoundV2(monkeysStart, counter::onInspection)
    }
    return counter.calculateMonkeyBusines()
}