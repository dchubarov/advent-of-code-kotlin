package org.twowls.lab.aoc.anno2023.day09

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println

fun Collection<Int>.extrapolateBothEnds(): Pair<Int, Int> {
    if (isEmpty())
        return 0 to 0

    val stack = mutableListOf<Pair<Int, Int>>()
    var diff = this
    do {
        var hasNonZero = false
        diff = diff.zipWithNext().map {
            val delta = it.second - it.first
            if (delta != 0) {
                hasNonZero = true
            }
            delta
        }
        if (hasNonZero) {
            stack += diff.first() to diff.last()
        }
    } while (hasNonZero)

    var lo = 0
    var hi = 0
    for (i in stack.indices.reversed()) {
        lo = stack[i].first - lo
        hi += stack[i].second
    }

    return (first() - lo) to (last() + hi)
}

fun main() {
    check(listOf(0, 3, 6, 9, 12, 15).extrapolateBothEnds() == -3 to 18)
    check(listOf(1, 3, 6, 10, 15, 21).extrapolateBothEnds() == 0 to 28)
    check(listOf(10, 13, 16, 21, 30, 45).extrapolateBothEnds() == 5 to 68)

    // Part 1 & 2
    cachedInput(year = 2023, day = 9)
        .readLines()
        .map { it.split(" ").filter(String::isNotBlank).map(String::toInt).extrapolateBothEnds() }
        .reduce { acc, p -> (acc.first + p.first) to (acc.second + p.second) }
        .println()
}
