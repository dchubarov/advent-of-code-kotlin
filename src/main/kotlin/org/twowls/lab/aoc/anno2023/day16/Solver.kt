package org.twowls.lab.aoc.anno2023.day16

import org.twowls.lab.aoc.common.RowCol
import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println
import kotlin.math.max
import kotlin.system.measureTimeMillis

internal fun traceContraptionLayout(
    layout: List<String>,
    beamStart: RowCol = RowCol(0, -1),
    beamDirection: RowCol = RowCol(0, 1)
): Int {
    val visited = mutableMapOf<RowCol, MutableSet<RowCol>?>()
    val width = layout[0].length

    data class Beam(val pos: RowCol, val inc: RowCol) {
        fun advanceToNextObstacle(): List<Beam> {
            var last = pos
            var next: RowCol?
            do {
                next = RowCol(last.row + inc.row, last.col + inc.col)
                if (next.row !in layout.indices || next.col !in 0..<width)
                    return emptyList()

                if (layout[next.row][next.col] == '.') {
                    visited[next] = null
                    last = next
                    next = null
                }
            } while (next == null)

            val approaches = visited[next]
            if (approaches == null) {
                visited[next] = mutableSetOf(last)
            } else {
                if (!approaches.contains(last))
                    approaches += last
                else
                    return emptyList()
            }

            return when (layout[next.row][next.col]) {
                '-' -> if (inc.row == 0) listOf(Beam(next, inc)) else split(next)
                '|' -> if (inc.col == 0) listOf(Beam(next, inc)) else split(next)
                '\\' -> reflectCounterclockwise(next)
                '/' -> reflectClockwise(next)
                else -> emptyList()
            }
        }

        private fun split(at: RowCol): List<Beam> = listOf(
            Beam(at, RowCol(if (inc.row == 0) 1 else 0, if (inc.col == 0) 1 else 0)),
            Beam(at, RowCol(if (inc.row == 0) -1 else 0, if (inc.col == 0) -1 else 0)),
        )

        private fun reflectClockwise(at: RowCol) = listOf(
            Beam(at, RowCol(if (inc.row == 0) -inc.col else 0, if (inc.col == 0) -inc.row else 0))
        )

        private fun reflectCounterclockwise(at: RowCol) = listOf(
            Beam(at, RowCol(if (inc.row == 0) inc.col else 0, if (inc.col == 0) inc.row else 0))
        )
    }

    var beams = listOf(Beam(beamStart, beamDirection))
    do {
        beams = beams.flatMap { it.advanceToNextObstacle() }
    } while (beams.isNotEmpty())

    return visited.size
}

fun main() {
    val sampleInput = listOf(
        ".|...\\....",
        "|.-.\\.....",
        ".....|-...",
        "........|.",
        "..........",
        ".........\\",
        "..../.\\\\..",
        ".-.-/..|..",
        ".|....-|.\\",
        "..//.|...."
    )
    check(traceContraptionLayout(sampleInput) == 46)

    val testInput = cachedInput(year = 2023, day = 16).useLines {
        it.filter(String::isNotBlank).toList()
    }

    // Part 01
    traceContraptionLayout(testInput).println()

    // Part 02
    val elapsedMillis = measureTimeMillis {
        max(
            testInput.indices.maxOf {
                max(
                    traceContraptionLayout(testInput, RowCol(it, -1), RowCol(0, 1)),
                    traceContraptionLayout(testInput, RowCol(it, testInput[0].length), RowCol(0, -1))
                )
            },

            (0..<testInput[0].length).maxOf {
                max(
                    traceContraptionLayout(testInput, RowCol(-1, it), RowCol(1, 0)),
                    traceContraptionLayout(testInput, RowCol(testInput.size, it), RowCol(-1, 0)),
                )
            }).println()
    }

    println("Elapsed time $elapsedMillis ms")
}