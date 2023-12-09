package org.twowls.lab.aoc.anno2023.day08

import org.twowls.lab.aoc.common.lcmBig
import org.twowls.lab.aoc.common.println
import org.twowls.lab.aoc.common.readInput

fun numberOfSteps(
    directions: String,
    waypoints: Map<String, Pair<String, String>>,
    entryWaypoint: String = "AAA",
    targetPredicate: (String) -> Boolean = { it == "ZZZ" }
): Int {
    var steps = 0
    var waypoint = entryWaypoint
    if (!targetPredicate(waypoint)) {
        var i = 0
        do {
            waypoint = (if (directions[i] == 'R') waypoints[waypoint]?.second else waypoints[waypoint]?.first)
                ?: throw IllegalStateException("Unreachable waypoint: $waypoint")
            i = if (i < directions.length - 1) i + 1 else 0
            steps++
        } while (!targetPredicate(waypoint))
    }
    return steps
}

fun numberOfGhostSteps(
    directions: String,
    waypoints: Map<String, Pair<String, String>>,
    entrypointPredicate: (String) -> Boolean = { it.endsWith('A') },
    targetPredicate: (String) -> Boolean = { it.endsWith('Z') }
): Long {
    val distances = waypoints.keys.filter(entrypointPredicate)
        .map { numberOfSteps(directions, waypoints, it, targetPredicate) }

    return distances.lcmBig().longValueExact()
}

fun main() {
    check(
        2 == numberOfSteps(
            "RL", mapOf(
                "AAA" to Pair("BBB", "CCC"),
                "BBB" to Pair("DDD", "EEE"),
                "CCC" to Pair("ZZZ", "GGG"),
                "DDD" to Pair("DDD", "DDD"),
                "EEE" to Pair("EEE", "EEE"),
                "GGG" to Pair("GGG", "GGG"),
                "ZZZ" to Pair("ZZZ", "ZZZ")
            ),
            "AAA"
        )
    )

    check(
        6 == numberOfSteps(
            "LLR", mapOf(
                "AAA" to Pair("BBB", "BBB"),
                "BBB" to Pair("AAA", "ZZZ"),
                "ZZZ" to Pair("ZZZ", "ZZZ")
            ),
            "AAA"
        )
    )

    check(
        6L == numberOfGhostSteps(
            "LR", mapOf(
                "11A" to Pair("11B", "XXX"),
                "11B" to Pair("XXX", "11Z"),
                "11Z" to Pair("11B", "XXX"),
                "22A" to Pair("22B", "XXX"),
                "22B" to Pair("22C", "22C"),
                "22C" to Pair("22Z", "22Z"),
                "22Z" to Pair("22B", "22B"),
                "XXX" to Pair("XXX", "XXX")
            )
        )
    )

    val fullInput = readInput("Day08")
    val headerLine = fullInput.indexOfFirst { it.isNotBlank() }.also { check(it >= 0) }
    val directions = fullInput[headerLine]
    val waypoints = mutableMapOf<String, Pair<String, String>>()
    val waypointRegex = Regex("^([A-Z]{3})\\s*=\\s*\\(([A-Z]{3})\\s*,\\s*([A-Z]{3})\\)$")
    for (i in headerLine + 1 until fullInput.size) {
        fullInput[i].takeIf { it.isNotBlank() }?.also {
            val match = waypointRegex.matchEntire(it)
                ?: throw IllegalArgumentException(it)
            waypoints += match.groupValues[1] to Pair(match.groupValues[2], match.groupValues[3])
        }
    }

    // Part 1
    numberOfSteps(directions, waypoints, "AAA").println()

    // Part 2
    numberOfGhostSteps(directions, waypoints).println()
}