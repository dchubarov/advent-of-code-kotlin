package org.twowls.lab.aoc.anno2023.day10

import org.twowls.lab.aoc.common.println
import kotlin.io.path.Path

fun main() {

    val testInput = listOf(
        "-L|F7",
        "7S-7|",
        "L|7||",
        "-L-J|",
        "L|-JF"
    )

    Maze(testInput).traverseLoop(1 to 1).println()

    var start: Pair<Int, Int>? = null
    val fullInput = Path("data/Day10.txt").toFile().useLines {
        it.onEachIndexed { row, s ->
            s.indexOfFirst { c -> c == 'S' }.also { col ->
                if (col >= 0)
                    start = row to col
            }
        }.toList()
    }

    // Part 1
    Maze(fullInput).traverseLoop(start!!).println()
}
