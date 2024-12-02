package org.twowls.lab.aoc.anno2024.d02

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.whitespacePattern
import kotlin.math.abs
import kotlin.math.sign

/**
 * [Day 2](https://adventofcode.com/2024/day/2)
 */
fun main() {
    cachedInput(year = 2024, day = 2).useLines { lines ->
        var n = 1
        val count = lines
            .map { it.split(whitespacePattern).map(String::toInt) }
            .map(::isReportSafe)
            .onEach { println("${n++} $it") }
            .count { it.first }

        println(count) // 1 -> 534, 2 ->
    }

    println(isReportSafe(listOf(73, 71, 68, 67, 64, 61, 57)))
}

fun isReportSafe(report: List<Int>): Triple<Boolean, Int?, Int?> {
    if (report.size < 2) return Triple(true, null, null)

    var trend: Int? = null
    var skip: Int? = null
    var l = 0

    for (r in 1..<report.size) {
        if (l == skip) l++
        val delta = report[l] - report[r]
        if (trend == null) trend = delta.sign
        if (abs(delta) !in 1..3 || delta.sign != trend) {
            if (skip != null) return Triple(false, trend, skip)
            else {
                skip = r
                continue
            }
        }
        l++
    }

    return Triple(true, trend, skip)
}
