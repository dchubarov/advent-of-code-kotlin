package org.twowls.lab.aoc.anno2023.day02

import org.twowls.lab.aoc.common.println
import org.twowls.lab.aoc.common.readInput

/**
 * [Day 2](https://adventofcode.com/2023/day/2)
 *
 * @author dchubarov
 */
fun main() {
    data class Subset(val redCount: Int = 0, val blueCount: Int = 0, val greenCount: Int = 0)
    data class Game(val id: Int, val subsets: List<Subset>)

    val regexHeader = Regex("^Game\\s+(\\d+):")
    val regexBody = Regex("\\s+(\\d+)\\s+(red|green|blue)\\s*([,;]|$)")

    fun parseGame(input: String): Game {
        val headerMatch = regexHeader.matchAt(input, index = 0)
            ?: throw IllegalArgumentException("Invalid header format")

        val subsets = mutableListOf<Subset>()
        var pos = headerMatch.range.last + 1
        var currentSubset = Subset()

        while (pos < input.length) {
            val bodyMatch = regexBody.matchAt(input, pos)
                ?: throw IllegalArgumentException("Invalid body format at index $pos: ${input.substring(pos)}")

            val increment = bodyMatch.groupValues[1].toInt()
            currentSubset = when (bodyMatch.groupValues[2]) {
                "red" -> currentSubset.copy(redCount = currentSubset.redCount + increment)
                "blue" -> currentSubset.copy(blueCount = currentSubset.blueCount + increment)
                "green" -> currentSubset.copy(greenCount = currentSubset.greenCount + increment)
                else -> currentSubset
            }

            if (bodyMatch.groupValues[3] == ";" || bodyMatch.groupValues[3] == "") {
                subsets += currentSubset
                currentSubset = Subset()
            }

            pos = bodyMatch.range.last + 1
        }

        return Game(headerMatch.groupValues[1].toInt(), subsets.toList())
    }

    fun parseAllGames(input: List<String>): List<Game> =
        input.map(::parseGame)

    fun sumPossibleGames(games: List<Game>, maxRed: Int, maxGreen: Int, maxBlue: Int): Int =
        games.filter { game ->
            game.subsets.maxOf { it.redCount } <= maxRed &&
                    game.subsets.maxOf { it.greenCount } <= maxGreen &&
                    game.subsets.maxOf { it.blueCount } <= maxBlue
        }.sumOf { it.id }

    fun sumGamePower(games: List<Game>): Int =
        games.sumOf { game ->
            game.subsets.maxOf { if (it.redCount == 0) 1 else it.redCount } *
                    game.subsets.maxOf { if (it.greenCount == 0) 1 else it.greenCount } *
                    game.subsets.maxOf { if (it.blueCount == 0) 1 else it.blueCount }
        }

    val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )
    parseAllGames(testInput).also {
        check(sumPossibleGames(it, maxRed = 12, maxGreen = 13, maxBlue = 14) == 8)
        check(sumGamePower(it) == 2286)
    }

    val fullInput = readInput("Day02")
    parseAllGames(fullInput).also {
        sumPossibleGames(it, maxRed = 12, maxGreen = 13, maxBlue = 14).println()
        sumGamePower(it).println()
    }
}