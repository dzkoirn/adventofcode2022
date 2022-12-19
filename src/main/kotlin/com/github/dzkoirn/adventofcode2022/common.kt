package com.github.dzkoirn.adventofcode2022

data class Point(
    val x: Int,
    val y: Int,
)

class PointComparator : Comparator<Point> {
    override fun compare(left: Point?, right: Point?): Int {
        val result = (left?.x ?: 0) - (right?.x ?: 0)
        return if(result != 0) {
            result
        } else {
            (left?.y ?: 0) - (right?.y ?: 0)
        }
    }

}

typealias Matrix = Array<CharArray>

typealias Input = List<String>

fun createMatrix(width: Int, height: Int, value: Char = ' '): Matrix {
    return Array(height) {
        CharArray(width) { value }
    }
}

fun Matrix.put(x: Int, y: Int, value: Char) {
    this[y][x] = value
}

fun Matrix.get(x: Int, y: Int) = this[y][x]