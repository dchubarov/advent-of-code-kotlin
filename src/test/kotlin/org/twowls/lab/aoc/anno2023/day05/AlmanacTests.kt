package org.twowls.lab.aoc.anno2023.day05

import org.twowls.lab.aoc.anno2023.day05.Almanac
import org.twowls.lab.aoc.anno2023.day05.addRange
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AlmanacTests {
    private lateinit var almanac: Almanac

    @BeforeTest
    fun setup() {
        almanac = Almanac().apply {
            namedMapper("seed-to-soil")
                .addRange(50, 98, 2)
                .addRange(52, 50, 48)
            namedMapper("soil-to-fertilizer")
                .addRange(0, 15, 37)
                .addRange(37, 52, 2)
                .addRange(39, 0, 15)
            namedMapper("fertilizer-to-water")
                .addRange(49, 53, 8)
                .addRange(0, 11, 42)
                .addRange(42, 0, 7)
                .addRange(57, 7, 4)
            namedMapper("water-to-light")
                .addRange(88, 18, 7)
                .addRange(18, 25, 70)
            namedMapper("light-to-temperature")
                .addRange(45, 77, 23)
                .addRange(81, 45, 19)
                .addRange(68, 64, 13)
            namedMapper("temperature-to-humidity")
                .addRange(0, 69, 1)
                .addRange(1, 0, 69)
            namedMapper("humidity-to-location")
                .addRange(60, 56, 37)
                .addRange(56, 93, 4)
        }
    }

    @Test
    fun test1() {
        assertEquals(82, almanac.mapValue(79, ::logMapper))
        assertEquals(43, almanac.mapValue(14))
        assertEquals(86, almanac.mapValue(55))
        assertEquals(35, almanac.mapValue(13))
    }

    @Test
    fun testLowestLocation() {
        val seeds = listOf(79L, 14, 55, 13)
        assertEquals(35, seeds.minOf(almanac::mapValue))
    }

    @Test
    fun testLowestLocationRange() {
        val seeds = listOf(79L, 14, 55, 13)

        assertEquals(46, seeds
            .chunked(2) { it[0] to it[1] }
            .flatMap {
                val start = it.first
                List(it.second.toInt()) { index -> start + index }
            }
            .minOf(almanac::mapValue))
    }

    private fun logMapper(mapper: String, oldValue: Long, mappedValue: Long) {
        println("$mapper: $oldValue -> $mappedValue")
    }
}