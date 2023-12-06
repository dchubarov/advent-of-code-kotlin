package day05

import println
import readInput

fun main() {
    val almanac = Almanac()
    val seeds = mutableListOf<Long>()
    parseAlmanacInput(readInput("Day05"), almanac, seeds)

    // Part 01
    seeds.minOf(almanac::mapValue).println()

    // Part 02
    seeds.asSequence()
        .chunked(2) { it[0] to it[1] }
        .onEach { println("Processing seeds ${it.first} to ${it.first + it.second - 1}...") }
        .flatMap {
            var count = it.second
            generateSequence(it.first) { value -> if (--count > 0) value + 1 else null }
        }
        .minOf(almanac::mapValue)
        .println()
}