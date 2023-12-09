package org.twowls.lab.aoc.anno2023.day04

internal data class Card(val id: Int, val winning: Set<Int>, val available: Set<Int>) {
    fun points() = available
        .intersect(winning)
        .fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2 }

    companion object {
        fun parseCard(s: String): Card {
            val winning = mutableSetOf<Int>()
            val available = mutableSetOf<Int>()
            var watershed = false
            var cardId = 0

            for ((i, chunk) in s.split(Regex("\\s+")).withIndex()) {
                if (i == 0) {
                    if (chunk != "Card") throw IllegalArgumentException("No card header")
                    continue
                }
                if (i == 1) {
                    if (!chunk.endsWith(":")) throw IllegalArgumentException("Invalid card header")
                    cardId = chunk.removeSuffix(":").toInt()
                    continue
                }

                if (chunk == "|") {
                    watershed = true
                } else {
                    val n = chunk.toInt()
                    if (watershed)
                        available += n
                    else
                        winning += n
                }
            }

            return Card(cardId, winning.toSet(), available.toSet())
        }
    }
}
