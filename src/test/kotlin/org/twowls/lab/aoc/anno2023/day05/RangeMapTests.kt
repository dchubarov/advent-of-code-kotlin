package org.twowls.lab.aoc.anno2023.day05

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class RangeMapTests {
    @Test
    fun test1() {
        val map = TreeMap<Long, LongRange>()
            .addRange(50, 98, 2)
            .addRange(52, 50, 48)

        assertEquals(50, map.mapValue(98))
        assertEquals(51, map.mapValue(99))
        assertEquals(55, map.mapValue(53))
        assertEquals(81, map.mapValue(79))
        assertEquals(57, map.mapValue(55))
        assertEquals(14, map.mapValue(14))
        assertEquals(13, map.mapValue(13))
        assertEquals(0, map.mapValue(0))
        assertEquals(-100, map.mapValue(-100))
        assertEquals(Long.MIN_VALUE, map.mapValue(Long.MIN_VALUE))
        assertEquals(Long.MAX_VALUE, map.mapValue(Long.MAX_VALUE))
    }
}