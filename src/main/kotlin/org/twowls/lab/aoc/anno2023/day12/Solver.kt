package org.twowls.lab.aoc.anno2023.day12

import org.twowls.lab.aoc.common.println

fun main() {
    val t = "????.#...#..."
    val g = listOf(4, 1, 1)

    fun isArrangementPossibleAt(s: String, index: Int, len: Int, predecessor: Char? = null): Char? {
        if (index + len > s.length)
            return null

        val successor = if (index + len < s.length - 1) s[index + len] else null
        for (j in 0..<len) {
            when (s[j + index]) {
                '?' -> {

                }
                '#' -> {

                }
                else -> break
            }
        }

        return null
    }

    isArrangementPossibleAt(t, 1, g[0]).println()

}