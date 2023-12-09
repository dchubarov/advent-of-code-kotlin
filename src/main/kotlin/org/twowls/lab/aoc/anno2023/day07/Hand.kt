package org.twowls.lab.aoc.anno2023.day07

val DEFAULT_DECK = setOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
val JOKER_DECK = setOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

internal class Hand(
    private val covers: String,
    deck: Set<Char> = DEFAULT_DECK,
    wildcard: Char = Char.MIN_VALUE
) : Comparable<Hand> {

    private val strength: Int
    private val cards: String

    init {
        require(covers.length == 5)
        require(wildcard == Char.MIN_VALUE || wildcard in deck)
        val frequencies = IntArray(deck.size)
        val cardsBuilder = StringBuilder()
        for (c in covers) {
            deck.indexOf(c).let {
                check(it >= 0)
                frequencies[it]++
                cardsBuilder.append(it.toString(16))
            }
        }

        var maxStrength = calculateStrength(frequencies)
        strength = if (wildcard == Char.MIN_VALUE)
            maxStrength
        else {
            val wildcardIndex = deck.indexOf(wildcard)
            if (frequencies[wildcardIndex] > 0) {
                val wildcardFreq = frequencies[wildcardIndex]
                frequencies[wildcardIndex] = 0

                for (i in frequencies.indices) {
                    if (frequencies[i] > 0) {
                        frequencies[i] += wildcardFreq
                        calculateStrength(frequencies).let {
                            if (it > maxStrength)
                                maxStrength = it
                        }
                        frequencies[i] -= wildcardFreq
                    }
                }
            }
            maxStrength
        }

        cards = cardsBuilder.toString()
    }

    private fun calculateStrength(a: IntArray) =
        a.fold(initial = 0) { acc: Int, value: Int -> acc + value * value }

    override fun compareTo(other: Hand): Int =
        compareValuesBy(this, other, { it.strength }, { it.cards })

    override fun toString(): String {
        return "Hand{covers=$covers, strength=$strength}"
    }
}
