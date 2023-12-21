package org.twowls.lab.aoc.anno2023.day19

import org.twowls.lab.aoc.common.cachedInput
import org.twowls.lab.aoc.common.println
import kotlin.system.measureTimeMillis

fun main() {
    val workflows = mutableMapOf<String, Workflow>()
    val parts = mutableListOf<Part>()

    cachedInput(year = 2023, day = 19).useLines { lines ->
        var partsInput = false
        for ((ln, line) in lines.withIndex()) {
            if (line.isBlank()) {
                partsInput = true
                continue
            }

            val i = line.indexOf('{')
            check(i >= 0) { "Line ${ln + 1}: expected '{" }
            val j = line.indexOf('}', startIndex = i + 1)
            check(j > i) { "Line ${ln + 1}: expected '}'" }

            if (partsInput) {
                try {
                    parts += Part.fromString(line.substring(i + 1, j))
                } catch (e: Exception) {
                    throw IllegalStateException("Line ${ln + 1} part parse exception: ${e.localizedMessage}", e)
                }
            } else {
                val workflowName = line.substring(0, i)
                check(workflowName.isNotBlank()) { "Line ${ln + 1}: missing workflow name" }
                try {
                    workflows[workflowName] = Workflow.fromString(line.substring(i + 1, j))
                } catch (e: Exception) {
                    throw IllegalStateException("Line ${ln + 1} workflow parse exception: ${e.localizedMessage}", e)
                }
            }
        }
    }

    println("Statistics: parts=${parts.size} workflows=${workflows.size}")
    val pipeline = SimplePipeline(workflows)
    val elapsed = measureTimeMillis {
        // Part 01
        parts.sumOf {
            pipeline.runPart(it) { part, result -> if (result == ACCEPT) part.summary else 0 }
        }.println()

        // Part 02
        // We need to have a tree of nodes instead of map, and we should travel all the success paths
        // from ACCEPTED leafs to the root, collecting value ranges for all characteristics. The those
        // ranges should be normalized, and we know quantity of possible value of x, m, a, and s.
    }
    println("Elapsed time $elapsed ms")
}