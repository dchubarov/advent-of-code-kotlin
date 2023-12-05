package day05

import java.util.*

class Almanac {
    private val rangeComposite = listOf(
        "seed-to-soil",
        "soil-to-fertilizer",
        "fertilizer-to-water",
        "water-to-light",
        "light-to-temperature",
        "temperature-to-humidity",
        "humidity-to-location"
    ).associateWithTo(LinkedHashMap<String, RangeMap>()) { TreeMap() }

    fun namedMapper(name: String) =
        rangeComposite[name] ?: throw IllegalArgumentException("Mapper not found: $name")

    fun mapValue(
        sourceValue: Long,
        observer: (key: String, oldValue: Long, mappedValue: Long) -> Unit = { _, _, _ -> }
    ): Long =
        rangeComposite.entries.fold(sourceValue) { acc, entry ->
            val result = entry.value.mapValue(acc)
            observer(entry.key, acc, result)
            result
        }
}
