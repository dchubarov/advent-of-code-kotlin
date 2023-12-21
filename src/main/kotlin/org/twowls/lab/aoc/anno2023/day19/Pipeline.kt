package org.twowls.lab.aoc.anno2023.day19

internal const val ENTRYPOINT = "in"
internal const val ACCEPT = "A"
internal const val REJECT = "R"

internal data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
    val summary = x + m + a + s

    companion object {
        fun fromString(input: String): Part {
            var x = 0
            var m = 0
            var a = 0
            var s = 0

            if (input.isNotBlank()) {
                for (characteristic in input.split(',')) {
                    val nameAndValue = characteristic.split('=')
                    check(nameAndValue.size == 2) { "expected expression <characteristic>=<value>, but got $characteristic" }
                    val inc = nameAndValue[1].toInt()
                    when (nameAndValue[0]) {
                        "x" -> x += inc
                        "m" -> m += inc
                        "a" -> a += inc
                        "s" -> s += inc
                        else -> throw IllegalStateException("invalid characteristic '${nameAndValue[0]}'")
                    }
                }
            }

            return Part(x, m, a, s)
        }
    }
}

internal enum class Operator { GREATER_THAN, LESS_THAN }

interface Rule<T> {
    val destination: String
    fun test(obj: T): Boolean
}

internal data class PartRule(
    val lValSupplier: Part.() -> Int,
    val op: Operator, val rVal: Int,
    override val destination: String
) : Rule<Part> {

    override fun test(obj: Part): Boolean {
        val lVal = lValSupplier(obj)
        return when (op) {
            Operator.LESS_THAN -> lVal < rVal
            Operator.GREATER_THAN -> lVal > rVal
        }
    }

    companion object {
        private val rulePattern = Regex("^([xmas])([><])(\\d+):(\\S+)$")
        fun fromString(input: String): PartRule {
            val match = rulePattern.matchEntire(input)
                ?: throw IllegalStateException("expression does not match rule pattern: $input")

            val lvf = when (match.groupValues[1]) {
                "x" -> Part::x
                "m" -> Part::m
                "a" -> Part::a
                "s" -> Part::s
                else -> throw IllegalStateException()
            }

            return PartRule(
                lvf,
                if (match.groupValues[2] == ">") Operator.GREATER_THAN else Operator.LESS_THAN,
                match.groupValues[3].toInt(),
                match.groupValues[4]
            )
        }
    }
}

internal data class Workflow(val rules: List<Rule<*>>, val elseDestination: String) {
    fun runPart(part: Part): String {
        for (rule in rules) {
            if (rule is PartRule && rule.test(part))
                return rule.destination
        }
        return elseDestination
    }

    companion object {
        fun fromString(input: String): Workflow {
            val inputRules = input.split(',')
            check(inputRules.size > 1) { "expected on or more rule, but got input $input" }

            val rules = mutableListOf<PartRule>()
            (0..<(inputRules.size - 1)).forEach { rules += PartRule.fromString(inputRules[it]) }
            return Workflow(rules, inputRules.last())
        }
    }
}

internal interface Pipeline {
    fun <R> runPart(part: Part, block: (Part, String) -> R): R
}

internal data class SimplePipeline(val workflows: Map<String, Workflow>) : Pipeline {
    override fun <R> runPart(part: Part, block: (Part, String) -> R): R {
        var step = ENTRYPOINT
        while (step != ACCEPT && step != REJECT) {
            val w = workflows[step] ?: throw IllegalStateException("Workflow not found: $step")
            step = w.runPart(part)
        }
        return block(part, step)
    }
}
