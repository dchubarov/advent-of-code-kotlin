package org.twowls.lab.aoc.anno2024.d03

import org.twowls.lab.aoc.common.cachedInput

fun main() {
    val input = cachedInput(year = 2024, day = 3).readText()
    val re = Regex("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")
    var enabled = true

    val s = re.findAll(input)
        //.onEach { println("${it.groupValues} $enabled") }
        .map {
            if (it.groupValues[0].startsWith("mul")) {
                return@map if (enabled)
                    it.groupValues[1].toInt() * it.groupValues[2].toInt()
                else 0
            }

            enabled = !it.groupValues[0].startsWith("don't")
            0
        }
        .sum()

    println(s) // 1->174103751, 2->100411201
}
