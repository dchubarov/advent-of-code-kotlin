package day06

import println

fun main() {
    fun bsMost(t: Int, d: Long): Int {
        var result: Int = -1
        var lo = 0
        var hi = t
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            if ((t - mid).toLong() * mid > d) {
                lo = mid + 1
                result = mid
            } else {
                hi = mid - 1
            }
        }
        return result
    }

    fun bsLeast(t: Int, d: Long): Int {
        var result: Int = -1
        var lo = 0
        var hi = t
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            if ((t - mid).toLong() * mid > d) {
                hi = mid - 1
                result = mid
            } else {
                lo = mid + 1
            }
        }
        return result
    }

    // Part 1
    listOf(55 to 246, 82 to 1441, 64 to 1012, 90 to 1111)
        .map { bsLeast(it.first, it.second.toLong()) to bsMost(it.first, it.second.toLong()) }
        .map { it.second - it.first + 1 }
        .fold(1) { acc, i -> acc * i }
        .println()

    // Part 2
    val t = 55826490
    val d = 246144110121111
    val v = bsMost(t, d) - bsLeast(t, d) + 1
    println(v)
}