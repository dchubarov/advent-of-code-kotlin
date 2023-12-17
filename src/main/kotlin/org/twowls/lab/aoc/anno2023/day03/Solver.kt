package org.twowls.lab.aoc.anno2023.day03

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println

fun findPartNumbers(input: List<String>) = sequence {
    val sc = Schematic(input)
    for ((_, pos) in sc.symbolPositions) {
        sc.findAdjacentNumbers(pos, remove = true).forEach { yield(it) }
    }
}

fun findGearRatios(input: List<String>) = sequence {
    val sc = Schematic(input)
    for ((symbol, pos) in sc.symbolPositions) {
        if (symbol == '*') {
            val adjacentNumbers = sc.findAdjacentNumbers(pos)
            if (adjacentNumbers.size == 2)
                yield(adjacentNumbers[0] * adjacentNumbers[1])
        }
    }
}

fun main() {
    val testInput = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    )
    check(findPartNumbers(testInput).sum() == 4361)
    check(findGearRatios(testInput).sum() == 467835)

    val fullInput = cachedInput(year = 2023, day = 3).readLines()
    findPartNumbers(fullInput).sum().println()
    findGearRatios(fullInput).sum().println()
}