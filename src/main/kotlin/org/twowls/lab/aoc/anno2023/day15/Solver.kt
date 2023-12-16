package org.twowls.lab.aoc.anno2023.day15

import org.twowls.lab.aoc.common.println
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader

fun main() {
    fun CharSequence.hash(): Int =
        map { it.code }.fold(initial = 0) { acc, value -> 17 * (acc + value) % 256 }

    check("HASH".hash() == 52)

    data class Instruction(val label: String, val focalLength: Int, val hashValue: Int) {
        val box = label.hash()
    }

    fun generateInstructions(input: Sequence<Char>) = sequence {
        val sb = StringBuilder()
        var delimiter = -1

        input.forEachIndexed { index, ch ->
            if (ch == '=' || ch == '-')
                delimiter = sb.length

            if (ch != 0.toChar() && ch != ',' && !ch.isWhitespace())
                sb.append(ch)

            if (ch == 0.toChar() || ch == ',') {
                yield(Instruction(
                    label = sb.substring(0..<delimiter),
                    focalLength = sb.substring(delimiter + 1).takeIf(String::isNotEmpty)?.toInt() ?: -1,
                    hashValue = sb.hash()))

                delimiter = -1
                sb.clear()
            }
        }
    }

    val boxes = List(256) { mutableMapOf<String, Int>() }

//    val sampleInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
    val sampleInput = sequence {
        Path("data/Day15.txt").bufferedReader().use {
            do {
                val c = it.read()
                if (c >= 0)
                    yield(c.toChar())
                else
                    yield(0.toChar())
            } while (c >= 0)
        }
    }

    generateInstructions(sampleInput)
        .onEach {
            boxes[it.box].apply {
                if (it.focalLength >= 0)
                    put(it.label, it.focalLength)
                else
                    remove(it.label)
            }
        }
        .sumOf { it.hashValue }.println()

    boxes
        .mapIndexed { box, m -> m.values.mapIndexed { slot, f -> (box + 1) * (slot + 1) * f }.sum() }
        .sum()
        .println()
}