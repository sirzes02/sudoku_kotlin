package sudoku.problemDomain

import java.util.*

class Coordinates(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as Coordinates

        return x == that.x && y == that.y
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }
}
