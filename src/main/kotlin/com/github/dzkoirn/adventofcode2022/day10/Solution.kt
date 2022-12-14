package com.github.dzkoirn.adventofcode2022.day10

import com.github.dzkoirn.adventofcode2022.readInput
import java.util.LinkedList
import java.util.Queue
import kotlin.math.absoluteValue

fun main() {
    val input = readInput("day_10_input")
    println("Start")
    println("Puzzle 1")
    val result = getValuesOnSpecificTicks(input, defaultTicksToCare)
        .calculateSignalStrength()
    println(result)
    println("===================")
    println("Puzzle 2")
    val text = getImage(input)
    println(text)
    println("Finish")
}

sealed class Command {
    object Noop : Command()
    data class Add(val value: Int) : Command()
}

fun Command.asAdd() = this as Command.Add

fun parseInput(input: List<String>): Queue<Command> =
    input.map {
        if (it.startsWith("noop")) {
            Command.Noop
        } else {
            Command.Add(it.split(" ")[1].toInt())
        }
    }.let { LinkedList(it) }

fun execute(commands: Queue<Command>, onTick: (Long, Int) -> Unit = { _, _ -> }): Int {
    var register = 1
    var tick = 0L
    val pipeline = LinkedList<Command>()

    do {
        val command = if (pipeline.isNotEmpty()) {
            null
        } else if (commands.isEmpty()) {
            return register
        } else {
            commands.poll()
        }
        onTick(++tick, register)
        if (command == null) {
            register += pipeline.poll().asAdd().value
        } else if (command is Command.Add) {
            pipeline.add(command)
        }
    } while (true)
}

val defaultTicksToCare = arrayOf(20L, 60L, 100L, 140L, 180L, 220L)

fun getValuesOnSpecificTicks(
    input: List<String>,
    ticksToCare: Array<Long> = defaultTicksToCare
): List<Pair<Long, Int>> {
    val collector = TicksValueCollector(ticksToCare, )
    execute(parseInput(input), collector::onTick)
    return collector.getValues()
}

fun List<Pair<Long, Int>>.calculateSignalStrength() =
    sumOf { (tick, value) -> tick * value }

class TicksValueCollector(
    private val ticksToCare : Array<Long>,
) {
    private val values = mutableListOf<Pair<Long, Int>>()
    fun onTick(tick: Long, value: Int) {
        if (tick in ticksToCare) {
            values.add(Pair(tick, value))
        }
    }

    fun getValues(): List<Pair<Long, Int>> = values
}

class EverythingCollector {
    private val values = mutableListOf<Pair<Long, Int>>()
    fun onTick(tick: Long, value: Int) {
        values.add(Pair(tick, value))
    }

    fun getValues(): List<Pair<Long, Int>> = values
}

class CrtCollector {

    private val text = mutableListOf<Char>()
    fun onTick(tick: Long, value: Int) {
        val rowPosition = (tick - 1) % 40
        val symbol = if ((rowPosition - value).absoluteValue > 1) {
            '.'
        } else {
            '#'
        }
        text.add(symbol)
    }

    fun getText(): String =
        text.windowed(size = 40, step = 40)
            .map { it.joinToString(separator = "") }
            .joinToString(separator = "\n")
}

fun getImage(input: List<String>): String {
    return with(CrtCollector()) {
        execute(parseInput(input), this::onTick)
        getText()
    }
}