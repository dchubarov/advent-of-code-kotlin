package org.twowls.lab.aoc.anno2023.day19

import kotlin.test.Test
import kotlin.test.assertEquals

class SimplePipelineTest {
    @Test
    fun sample() {
        val pipeline = SimplePipeline(
            mapOf(
                "px" to Workflow(
                    listOf(
                        PartRule(Part::a, Operator.LESS_THAN, 2006, "qkd"),
                        PartRule(Part::m, Operator.GREATER_THAN, 2090, ACCEPT)
                    ), elseDestination = "rfg"
                ),

                "pv" to Workflow(
                    listOf(
                        PartRule(Part::a, Operator.GREATER_THAN, 1716, REJECT)
                    ), elseDestination = ACCEPT
                ),

                "lnx" to Workflow(
                    listOf(
                        PartRule(Part::m, Operator.GREATER_THAN, 1548, ACCEPT)
                    ), elseDestination = ACCEPT
                ),

                "rfg" to Workflow(
                    listOf(
                        PartRule(Part::s, Operator.LESS_THAN, 537, "gd"),
                        PartRule(Part::x, Operator.GREATER_THAN, 2440, REJECT)
                    ), elseDestination = ACCEPT
                ),

                "qs" to Workflow(
                    listOf(
                        PartRule(Part::s, Operator.GREATER_THAN, 3448, ACCEPT)
                    ), elseDestination = "lnx"
                ),

                "qkd" to Workflow(
                    listOf(
                        PartRule(Part::x, Operator.LESS_THAN, 1416, ACCEPT)
                    ), elseDestination = "crn"
                ),

                "crn" to Workflow(
                    listOf(
                        PartRule(Part::x, Operator.GREATER_THAN, 2662, ACCEPT)
                    ), elseDestination = REJECT
                ),

                "in" to Workflow(
                    listOf(
                        PartRule(Part::s, Operator.LESS_THAN, 1351, "px")
                    ), elseDestination = "qqz"
                ),

                "qqz" to Workflow(
                    listOf(
                        PartRule(Part::s, Operator.GREATER_THAN, 2770, "qs"),
                        PartRule(Part::m, Operator.LESS_THAN, 1801, "hdj")
                    ), elseDestination = REJECT
                ),

                "gd" to Workflow(
                    listOf(
                        PartRule(Part::a, Operator.GREATER_THAN, 3333, REJECT)
                    ), elseDestination = REJECT
                ),

                "hdj" to Workflow(
                    listOf(
                        PartRule(Part::m, Operator.GREATER_THAN, 838, ACCEPT)
                    ), elseDestination = "pv"
                )
            )
        )


        val parts = listOf(
            Part(x = 787, m = 2655, a = 1222, s = 2876),
            Part(x = 1679, m = 44, a = 2067, s = 496),
            Part(x = 2036, m = 264, a = 79, s = 2244),
            Part(x = 2461, m = 1339, a = 466, s = 291),
            Part(x = 2127, m = 1623, a = 2188, s = 1013)
        )

        assertEquals(19114, parts.sumOf {
            pipeline.runPart(it) { p, result ->
                if (result == ACCEPT) p.summary else 0
            }
        })
    }
}