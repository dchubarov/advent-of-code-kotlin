package org.twowls.lab.aoc.anno2024.d01

import org.twowls.lab.aoc.common.cachedInput
import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.text.Regex

/**
 * [Day 1](https://adventofcode.com/2024/day/1)
 */
fun main() {
    val leftList = PriorityQueue<Int>()
    val rightList = PriorityQueue<Int>()
    val counts = mutableMapOf<Int, Int>()

    cachedInput(year = 2024, day = 1).useLines { lines ->
        lines.forEachIndexed { n, line ->
            val (l, r) = line.split(Regex("\\s+"), limit = 2)
                .map(String::toInt)
                .zipWithNext()
                .first()

            leftList += l
            rightList += r

            counts.compute(r) { _, v -> if (v != null) v + 1 else 1 }
        }
    }

    if (leftList.isEmpty() || leftList.size != rightList.size)
        throw Exception("Invalid input")

    var dist = 0L
    var sim = 0L
    do {
        val l = leftList.poll()
        val r = rightList.poll()
        if (l != null && r != null) {
            val d = abs(l - r)
            dist += d

            val s = counts[l] ?: 0
            sim += l * s
        }
    } while (l != null && r != null)

//    val dist = (leftList zip rightList).sumOf { p ->
//        val d = abs(p.first - p.second)
//        println("${p.first} ${p.second} $d")
//        d
//    }
//
    print("$dist, $sim") // 1646452, 23609874
}
