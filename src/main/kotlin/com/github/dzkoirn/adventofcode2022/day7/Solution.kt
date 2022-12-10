package com.github.dzkoirn.adventofcode2022.day7

import com.github.dzkoirn.adventofcode2022.readInput
import java.util.LinkedList

fun main() {
    val input = readInput("day_7_input")
    println("Start")
    println("Puzzle 1\n ${puzzle1(input)}")
    println("===============")
    println("Puzzle 2\n ${puzzle2(input)}")
    println("Finish")
}

sealed class Item {
   data class File(
       val name: String,
       val size: Int
   ) : Item()

   data class Dir(
       val name: String,
       val items: MutableList<Item> = mutableListOf(),
   ): Item()
}

internal fun parseInput(input: List<String>): Item.Dir {
    val rootNode = Item.Dir(name = "/")

    input.asSequence()
        .drop(1)
        .fold(LinkedList<Item.Dir>().apply { push(rootNode) }) { path, s ->
            parseLine(path, s)
            path
        }
    return rootNode
}

private fun parseLine(path: LinkedList<Item.Dir>, line: String) {
    val split = line.split(' ')
    val rootNode = path.peek()
    when {
        split.first() == "$" -> if (split[1] == "cd") {
            when(split[2]) {
                "/" -> {
                    path.last.also {
                        path.clear()
                        path.add(it)
                    }
                }
                ".." -> path.pop()
                else -> {
                    rootNode.items.filterIsInstance(Item.Dir::class.java)
                        .find { it.name == split[2] }
                        ?.let { path.push(it) } ?: throw IllegalStateException("Can't find dir ${split[2]} in ${rootNode.name}")
                }
            }
        }
        else -> if (split.first() == "dir") {
            Item.Dir(name = split[1])
        } else {
            Item.File(name = split[1], size = split[0].toInt())
        }.also { item -> rootNode.items.add(item) }
    }
}

internal fun Item.Dir.countDirSize(cache: Map<Item.Dir, Long> = mutableMapOf()): Long =
    items.sumOf {
        when (it) {
            is Item.Dir -> cache.getOrElse(it) { it.countDirSize() }
            is Item.File -> it.size.toLong()
        }
    }

internal fun Item.Dir.toIterable(): Iterable<Item> {
   return object : Iterable<Item> {
       private val rootItem = this@toIterable

       override fun iterator(): Iterator<Item> {
           return object : Iterator<Item> {

               private val stack = LinkedList<Iterator<Item>>().apply {
                   push(rootItem.items.iterator())
               }

               override fun hasNext(): Boolean {
                   do {
                       val hasNext = stack.peek().hasNext()
                       if (hasNext) {
                           return true
                       }
                       stack.pop()
                   } while (stack.isNotEmpty())
                   return false
               }

               override fun next(): Item {
                   return stack.peek().next().also {
                       if (it is Item.Dir) {
                           stack.push(it.items.iterator())
                       }
                   }
               }
           }
       }

   }
}

fun puzzle1(lines: List<String>): Long {
    val fileTree = parseInput(lines)
    return fileTree.toIterable()
        .filterIsInstance<Item.Dir>()
        .map { it.countDirSize() }
        .filter { it <= 100_000 }
        .sum()
}

fun puzzle2(lines: List<String>): Long {
    val totalSize = 70000000
    val requireSize = 30000000
    val fileTree = parseInput(lines)
    val cache: Map<Item.Dir, Long> = mutableMapOf()
    val usedSize = fileTree.countDirSize(cache)
    val spaceToBeFreed = requireSize - (totalSize - usedSize)
    return fileTree.toIterable()
        .filterIsInstance<Item.Dir>()
        .map { it.countDirSize(cache) }
        .filter { it > spaceToBeFreed }
        .min()
}
