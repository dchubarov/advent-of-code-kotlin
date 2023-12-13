package org.twowls.lab.aoc.anno2023.day13

internal data class Note(val data: List<String>) {
    val rows = object : Axis() {
        override val length: Int = data.size
        override val orthogonalLength: Int = data[0].length
        override val summarizeFactor: Int = 100
        override fun getAt(coaxialIndex: Int, oppositeIndex: Int): Char =
            data[coaxialIndex][oppositeIndex]
    }

    val columns = object : Axis() {
        override val length: Int = data[0].length
        override val orthogonalLength: Int = data.size
        override fun getAt(coaxialIndex: Int, oppositeIndex: Int): Char =
            data[oppositeIndex][coaxialIndex]
    }

    abstract inner class Axis {
        protected abstract val length: Int
        protected abstract val orthogonalLength: Int
        protected open val summarizeFactor: Int = 1

        protected abstract fun getAt(coaxialIndex: Int, oppositeIndex: Int): Char

        fun summarize(smudge: Int = 0): Int =
            (1..<length).sumOf { if (isMirrored(it, smudge)) it * summarizeFactor else 0 }

        private fun isMirrored(index: Int, smudge: Int): Boolean {
            var lo: Int = index - 1
            var hi: Int = index
            var s = 0

            while (lo >= 0 && hi < length) {
                s += compare(lo, hi)
                if (s > smudge) {
                    return false
                } else {
                    lo--
                    hi++
                }
            }

            return (s == smudge)
        }

        private fun compare(index1: Int, index2: Int): Int {
            var mismatchCount = 0
            for (i in 0..<orthogonalLength) {
                if (getAt(index1, i) != getAt(index2, i))
                    if (++mismatchCount > 1)
                        return mismatchCount
            }
            return mismatchCount
        }
    }
}