package org.twowls.lab.aoc.anno2023.day10

import kotlin.math.abs

internal data class RowCol(val row: Int, val col: Int) {
    override fun toString(): String {
        return "($row, $col)"
    }
}

internal class Maze2(
    private val data: List<String>
) {
    private val height = data.size
    private val width = data[0].length

    fun findLongestLoop(start: RowCol): List<RowCol>? {
        val loops = mutableListOf<List<RowCol>>()
        var paths = listOf(
            RowCol(start.row - 1, start.col),
            RowCol(start.row, start.col + 1),
            RowCol(start.row - 1, start.col),
            RowCol(start.row, start.col - 1)
        ).filter {
            isPossibleMove(it, data[it.row][it.col], it.row - start.row, it.col - start.col)
        }.map {
            mutableListOf(start, it)
        }

        do {
            paths = paths.mapNotNull { path ->
                nextMove(path[path.size - 1], path[path.size - 2])?.let {
                    if (it == start) {
                        loops += path
                        null
                    } else {
                        path += it
                        path
                    }
                }
            }
        } while (paths.isNotEmpty())

        return loops.maxByOrNull { it.size }
    }

    private fun nextMove(start: RowCol, previous: RowCol): RowCol? {
        check(isValidPosition(previous))
        if (!isValidPosition(start))
            return null

        val rowIncrement = start.row - previous.row
        val colIncrement = start.col - previous.col
        check(abs(rowIncrement) + abs(colIncrement) == 1)

        return null
    }

    private fun isPossibleMove(position: RowCol, pipeFrom: Char, rowIncrement: Int, colIncrement: Int): Boolean {
        if (!isValidPosition(position))
            return false

        val pipeTo = data[position.row][position.col]

        return when {
            rowIncrement < 0 -> when (pipeFrom) { // North direction
                'S', '|', 'L', 'J' -> pipeTo == '|' || pipeTo == '7' || pipeTo == 'F'
                else -> false
            }

            colIncrement > 0 -> when (pipeFrom) { // East direction
                'S', '-', 'L', 'F' -> pipeTo == '-' || pipeTo == 'J' || pipeTo == '7'
                else -> false
            }

            rowIncrement > 0 -> when (pipeFrom) { // South direction
                'S', '|', 'F', '7' -> pipeTo == '|' || pipeTo == 'L' || pipeTo == 'J'
                else -> false
            }

            colIncrement < 0 -> when (pipeFrom) {
                'S', '-', 'J', '7' -> pipeTo == '-' || pipeTo == 'L' || pipeTo == 'F'
                else -> false
            }

            else -> false
        }
    }

    private fun isValidPosition(pos: RowCol) =
        (pos.row in 0 until height) && (pos.col in 0 until width)
}