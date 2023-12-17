package org.twowls.lab.aoc.anno2023.day04

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println

fun main() {

    fun sumPoints(input: List<String>) =
        input.map(Card.Companion::parseCard).map(Card::points).sum()

    fun part2(input: List<String>): Int {
        val m = input
            .map(Card.Companion::parseCard)
            .associateBy(Card::id) { it.available.intersect(it.winning).count() }

        fun recursiveCopies(id: Int): Int {
            val n = m[id] ?: return 0
            var result = 0
            for (i in 1..n) {
                result += recursiveCopies(id + i)
            }
            return result + 1
        }

        return m.keys.fold(0) { acc, id -> acc + recursiveCopies(id) }
    }

    val testCard = Card(1000, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53))
    check(testCard.points() == 8)
    val testCard2 = Card(2000, setOf(87, 83, 26, 28, 32), setOf(88, 30, 70, 12, 93, 22, 82, 36))
    check(testCard2.points() == 0)

    val testInput = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
    )
    check(sumPoints(testInput) == 13)
    check(part2(testInput) == 30)

    val fullInput = cachedInput(year = 2023, day = 4).readLines()
    sumPoints(fullInput).println()
    part2(fullInput).println()
}
