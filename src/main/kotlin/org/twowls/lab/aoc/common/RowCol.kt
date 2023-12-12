package org.twowls.lab.aoc.common

data class RowCol(val row: Int, val col: Int) {
    override fun toString(): String {
        return "($row:$col)"
    }
}