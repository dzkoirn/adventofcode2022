package com.github.dzkoirn.adventofcode2022

import java.io.InputStream

fun openResourseAsStream(resourceName: String) =
    object {}.javaClass.classLoader.getResourceAsStream(resourceName)