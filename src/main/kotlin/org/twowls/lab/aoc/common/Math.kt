package org.twowls.lab.aoc.common

/**
 * Calculates Greatest Common Divisor (GCD) of two numbers.
 */
tailrec fun gcd(a: Long, b: Long): Long =
    if (b == 0L) a else gcd(b, a % b)

/**
 * Calculates Least Common Multiple (LCM) for array of integers.
 */
fun Iterable<Int>.lcm(): Long =
    map { it.toLong() }
        .reduce { acc, num -> acc * num / gcd(acc, num) }
