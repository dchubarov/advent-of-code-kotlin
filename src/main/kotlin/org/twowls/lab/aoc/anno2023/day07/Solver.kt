package org.twowls.lab.aoc.anno2023.day07

import org.twowls.lab.aoc.common.println
import org.twowls.lab.aoc.common.readInput

internal fun totalWinnings(data: List<Pair<Hand, Int>>) =
    data
        .sortedBy { it.first }
        .mapIndexed { index: Int, handAndBid: Pair<Hand, Int> -> handAndBid.second * (index + 1) }
        .sum()

fun main() {
    totalWinnings(listOf("32T3K" to 765, "T55J5" to 684, "KK677" to 28, "KTJJT" to 220, "QQQJA" to 483)
        .map { Hand(it.first, JOKER_DECK, 'J') to it.second }).println()

    val fullInput = readInput("Day07")
        .map {
            it.split(" ").let { parts ->
                parts[0] to parts[1].toInt()
            }
        }

    // Part 01
    totalWinnings(fullInput.map {
        Hand(it.first) to it.second
    }).println()

    // Part 02
    totalWinnings(fullInput.map {
        Hand(it.first, JOKER_DECK, 'J') to it.second
    }).println()
}