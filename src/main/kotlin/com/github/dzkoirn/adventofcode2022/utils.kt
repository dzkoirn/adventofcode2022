package com.github.dzkoirn.adventofcode2022

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun openResourseAsStream(resourceName: String): InputStream =
    requireNotNull(object {}.javaClass.classLoader.getResourceAsStream(resourceName))

fun InputStream.asReader() =
    BufferedReader(InputStreamReader(BufferedInputStream(this)))

fun readInput(resourceName: String): List<String> =
    openResourseAsStream("day_3_input").asReader().use { reader ->
        reader.lines().toList()
    }