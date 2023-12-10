package org.twowls.lab.aoc.anno2023.day10

internal class Maze(private val tiles: List<String>) {
    private val width = tiles[0].length
    private val height = tiles.size

    fun traverseLoop(startPosition: Pair<Int, Int>): Int {
        var (cu1: Cursor?, cu2: Cursor?) = Direction.entries
            .mapNotNull { Cursor(startPosition, it.opposite()).advanceOrNull(it) }
            .also { check(it.size == 2) }

        var step = 1
        while (cu1.position != cu2.position) {
            cu1 = cu1.advanceOrNull() ?: return -1
            cu2 = cu2.advanceOrNull() ?: return -1
            step++
        }
        return step
    }

    enum class Direction(
        val increment: Pair<Int, Int>
    ) {
        N(-1 to 0),
        E(0 to 1),
        S(1 to 0),
        W(0 to -1)
        ;

        fun areConnectableTiles(fromTile: Char, toTile: Char) = when (this) {
            N -> when (fromTile) {
                'S', '|', 'L', 'J' -> toTile == '|' || toTile == '7' || toTile == 'F'
                else -> false
            }

            E -> when (fromTile) {
                'S', '-', 'L', 'F' -> toTile == '-' || toTile == 'J' || toTile == '7'
                else -> false
            }

            S -> when (fromTile) {
                'S', '|', 'F', '7' -> toTile == '|' || toTile == 'L' || toTile == 'J'
                else -> false
            }

            W -> when (fromTile) {
                'S', '-', 'J', '7' -> toTile == '-' || toTile == 'L' || toTile == 'F'
                else -> false
            }
        }

        fun opposite() = when (this) {
            N -> S
            E -> W
            S -> N
            W -> E
        }
    }

    inner class Cursor(
        val position: Pair<Int, Int>,
        private val backwardDirection: Direction
    ) {
        private val tile = tiles[position.first][position.second]

        fun advanceOrNull(direction: Direction? = null): Cursor? {
            val nextDirection = nextDirection() ?: direction ?: return null
            val nextPosition = (position.first + nextDirection.increment.first) to
                    (position.second + nextDirection.increment.second)

            return if (nextPosition.first in 0 until height && nextPosition.second in 0 until width &&
                nextDirection.areConnectableTiles(tile, tiles[nextPosition.first][nextPosition.second])
            )
                Cursor(nextPosition, nextDirection.opposite())
            else
                null
        }

        override fun toString(): String {
            return "(${position.first} ${position.second})"
        }

        private fun nextDirection(): Direction? = when (tile) {
            '|' -> if (backwardDirection == Direction.S) Direction.N else Direction.S
            '-' -> if (backwardDirection == Direction.W) Direction.E else Direction.W
            'L' -> if (backwardDirection == Direction.N) Direction.E else Direction.N
            'J' -> if (backwardDirection == Direction.N) Direction.W else Direction.N
            'F' -> if (backwardDirection == Direction.S) Direction.E else Direction.S
            '7' -> if (backwardDirection == Direction.S) Direction.W else Direction.S
            else -> null
        }
    }
}