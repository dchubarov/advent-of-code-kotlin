package org.twowls.lab.aoc.anno2023.day13

import org.twowls.lab.aoc.common.println
import kotlin.io.path.Path
import kotlin.io.path.useLines

fun main() {
    val sampleNote1 = listOf(
        "#.##..##.",
        "..#.##.#.",
        "##......#",
        "##......#",
        "..#.##.#.",
        "..##..##.",
        "#.#.##.#."
    )
    check(Note(sampleNote1).columns.summarize() == 5)
    check(Note(sampleNote1).rows.summarize(smudge = 1) == 300)

    val sampleNote2 = listOf(
        "#...##..#",
        "#....#..#",
        "..##..###",
        "#####.##.",
        "#####.##.",
        "..##..###",
        "#....#..#"
    )
    check(Note(sampleNote2).rows.summarize() == 400)
    check(Note(sampleNote2).rows.summarize(smudge = 1) == 100)

    val notes = mutableListOf<Note>()
    Path("data/Day13.txt").useLines {
        var noteInput = mutableListOf<String>()
        it.forEach { line ->
            if (line.isNotBlank()) {
                noteInput += line
            } else {
                notes += Note(noteInput)
                noteInput = mutableListOf()
            }
        }
        if (noteInput.isNotEmpty()) {
            notes += Note(noteInput)
        }
    }

    println("Stats: note-count=${notes.size}")
    notes.sumOf { it.rows.summarize() + it.columns.summarize() }.println()
    notes.sumOf { it.rows.summarize(smudge = 1) + it.columns.summarize(smudge = 1) }.println()
}