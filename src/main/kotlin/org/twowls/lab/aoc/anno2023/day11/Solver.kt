package org.twowls.lab.aoc.anno2023.day11

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println
import java.util.*
import kotlin.math.abs

private const val GALAXY_CHAR = '#'

internal fun distance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int =
    abs(a.first - b.first) + abs((a.second - b.second))

fun main() {
    check(distance(6 to 1, 11 to 5) == 9)
    check(distance(0 to 4, 10 to 9) == 15)
    check(distance(2 to 0, 7 to 12) == 17)
    check(distance(11 to 0, 11 to 5) == 5)
    check(distance(2 to 18, 11 to 18) == 9)

    /*
    val testInput = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#....."
    )
    */
    val testInput = cachedInput(year = 2023, day = 11).readLines()

    val spaceRows = testInput.indices.toCollection(TreeSet())
    val spaceCols = (0..<testInput[0].length).toCollection(TreeSet())

    val galaxies = mutableListOf<Pair<Int, Int>>()
    testInput.forEachIndexed { row, line ->
        var col = line.indexOfFirst { it == GALAXY_CHAR }
        while (col >= 0) {
            spaceRows -= row
            spaceCols -= col
            galaxies += row to col
            col = line.indexOf('#', startIndex = col + 1)
        }
    }

    println("Statistics: galaxies = ${galaxies.size}, space rows = ${spaceRows.size}, space columns = ${spaceCols.size}")

    fun expand(g: Pair<Int, Int>, factor: Int): Pair<Int, Int> {
        val extraRows = spaceRows.headSet(g.first).count()
        val extraCols = spaceCols.headSet(g.second).count()
        return (g.first + extraRows * factor - extraRows) to (g.second + extraCols * factor - extraCols)
    }

    fun sumOfDistances(factor: Int): Long =
        galaxies.mapIndexed { i, g1 ->
            galaxies.drop(i + 1).sumOf { g2 ->
                distance(expand(g1, factor), expand(g2, factor)).toLong()
            }
        }.sum()

    // Part 1
    sumOfDistances(factor = 2).println()

    // Part 2
    sumOfDistances(factor = 1_000_000).println()
}