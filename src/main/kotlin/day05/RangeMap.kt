package day05

import java.util.*

typealias RangeMap = NavigableMap<Long, LongRange>

fun RangeMap.addRange(destinationStart: Long, sourceStart: Long, length: Long): RangeMap {
    this += sourceStart to LongRange(destinationStart, destinationStart + length - 1)
    return this
}

fun RangeMap.mapValue(sourceValue: Long): Long {
    val e = floorEntry(sourceValue)
        ?: return sourceValue

    val delta = sourceValue - e.key
    return if (e.value.first + delta <= e.value.last)
        e.value.first + delta
    else
        sourceValue
}
