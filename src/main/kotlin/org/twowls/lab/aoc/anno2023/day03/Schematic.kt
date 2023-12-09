package org.twowls.lab.aoc.anno2023.day03

internal class Schematic(private val input: List<String>) {
    val symbolPositions = mutableListOf<Pair<Char, Int>>()
    private val numberPositions = mutableListOf<IntRange>()
    private val width: Int = if (input.isNotEmpty()) input[0].length else 0

    init {
        if (width > 0)
            parseInput(input)
    }

    fun findAdjacentNumbers(pos: Int, remove: Boolean = false): List<Int> {
        val result = mutableListOf<Int>()
        val (r0, c0) = toRowColumn(pos)

        val iter = numberPositions.iterator()
        while (iter.hasNext()) {
            val numRange = iter.next()
            val (r1, c1) = toRowColumn(numRange.first)
            val (_, c2) = toRowColumn(numRange.last)

            var isAdjacent = false
            if (r1 == r0 && c0 > 0 && c1 == c0 + 1)
                isAdjacent = true
            else if (r1 == r0 && c0 < width - 1 && c2 == c0 - 1)
                isAdjacent = true
            else if ((r1 == r0 + 1 || r1 == r0 - 1) &&
                (c0 >= (c1 - 1).coerceAtLeast(0)) &&
                (c0 <= (c2 + 1).coerceAtMost(width - 1))
            )
                isAdjacent = true

            if (isAdjacent) {
                result += input[r1].substring(c1, c2 + 1).toInt()
                if (remove) {
                    iter.remove()
                }
            }
        }

        return result.toList()
    }

    private fun parseInput(input: List<String>) {
        for ((i, s) in input.withIndex()) {
            var numberStart = -1
            var numberEnd = -1

            for ((j, c) in s.withIndex()) {
                if (c.isDigit()) {
                    if (numberStart < 0) {
                        numberStart = j
                        numberEnd = j
                    } else
                        numberEnd = j

                    if (j < width - 1)
                        continue
                }

                if (numberStart >= 0) {
                    numberPositions += IntRange(toPosition(i, numberStart), toPosition(i, numberEnd))
                    numberStart = -1
                    numberEnd = -1
                }

                if (!c.isDigit() && c != '.') {
                    symbolPositions += Pair(c, toPosition(i, j))
                }
            }
        }
    }

    private fun toRowColumn(pos: Int) =
        Pair(pos / width, pos % width)

    private fun toPosition(row: Int, col: Int) =
        row * width + col
}
