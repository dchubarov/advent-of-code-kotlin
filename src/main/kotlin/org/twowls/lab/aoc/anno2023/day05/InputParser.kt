package org.twowls.lab.aoc.anno2023.day05

internal fun parseAlmanacInput(input: List<String>, almanac: Almanac, seeds: MutableList<Long>) {
    var activeMapper: RangeMap? = null

    for (line in input) {
        if (line.isBlank())
            continue

        if (line.startsWith("seeds:")) {
            seeds.addAll(parseNumberSequence(line, index = 6))
        } else if (line.endsWith(" map:")) {
            activeMapper = almanac.namedMapper(line.removeSuffix(" map:"))
        } else {
            if (activeMapper == null) throw IllegalStateException("No active mapper")
            val data = parseNumberSequence(line, desired = 3)
            activeMapper.addRange(data[0], data[1], data[2])
        }
    }
}

private val regexNumber = Regex("\\s*(\\d+)")

private fun parseNumberSequence(s: String, index: Int = 0, desired: Int = 0): List<Long> {
    val result = mutableListOf<Long>()
    var pos = index

    do {
        val match = regexNumber.matchAt(s, pos)
        if (match != null) {
            result += match.groupValues[1].toLong()
            if (desired > 0 && result.size >= desired)
                break
            pos = match.range.last + 1
        }
    } while (match != null)

    if (desired > 0 && result.size != desired)
        throw IllegalStateException("Expected $desired number(s), but got ${result.size}")

    return result
}