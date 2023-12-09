package org.twowls.lab.aoc.common

import java.math.BigInteger

/**
 * Calculates Least Common Multiple (LCM) for array of integers.
 */
fun Iterable<Int>.lcmBig(): BigInteger =
    map { BigInteger.valueOf(it.toLong()) }
        .reduce { acc, num -> acc * num / acc.gcd(num) }
