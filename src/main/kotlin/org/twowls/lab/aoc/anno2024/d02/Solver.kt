package org.twowls.lab.aoc.anno2024.d02

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.whitespacePattern
import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs
import kotlin.math.sign

/**
 * [Day 2](https://adventofcode.com/2024/day/2)
 */
fun main() {
    cachedInput(year = 2024, day = 2).useLines { lines ->
        val count = lines
            .map { it.split(whitespacePattern).mapTo(LinkedList(), String::toInt) }
            .count(::isReportSafe)

        println(count) // 1 -> 534, 2 ->
    }
}

fun isReportSafe(report: Queue<Int>): Boolean {
    if (report.size < 2) return true

    var i = report.poll()
    var trend: Int? = null

    while (report.isNotEmpty()) {
        val j = report.poll()
        val delta = i - j
        if (trend == null) trend = delta.sign

        if (abs(delta) !in 1..3 || delta.sign != trend) {
            return false
        }

        i = j
    }

   return true
}
