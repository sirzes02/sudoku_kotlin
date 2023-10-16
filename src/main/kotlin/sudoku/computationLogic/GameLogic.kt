package sudoku.computationLogic

import sudoku.constants.GameState
import sudoku.constants.Rows
import sudoku.problemDomain.SudokuGame
import java.util.*

object GameLogic {
    val newGame: SudokuGame
        get() = SudokuGame(GameState.NEW, GameGenerator.newGameGrid)

    fun checkForCompletion(grid: Array<IntArray>): GameState {
        if (sudokuIsInvalid(grid)) return GameState.ACTIVE

        return if (tileAreNotFilled(grid)) GameState.ACTIVE else GameState.COMPLETE
    }

    fun sudokuIsInvalid(grid: Array<IntArray>): Boolean {
        if (rowsAreInvalid(grid)) return true

        return if (columnsAreInvalid(grid)) true else squaresAreInvalid(grid)
    }

    private fun rowsAreInvalid(grid: Array<IntArray>): Boolean {
        for (yIndex in 1..SudokuGame.GRID_BOUNDARY) {
            val row: MutableList<Int?> = ArrayList()

            for (xIndex in 0..<SudokuGame.GRID_BOUNDARY) {
                row.add(grid[xIndex][yIndex])
            }

            if (collectionHasRepeats(row)) return true
        }

        return false
    }

    private fun columnsAreInvalid(grid: Array<IntArray>): Boolean {
        for (xIndex in 1..SudokuGame.GRID_BOUNDARY) {
            val row: MutableList<Int?> = ArrayList()

            for (yIndex in 0..<SudokuGame.GRID_BOUNDARY) {
                row.add(grid[xIndex][yIndex])
            }

            if (collectionHasRepeats(row)) return true
        }

        return false
    }

    private fun squaresAreInvalid(grid: Array<IntArray>): Boolean {
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true

        return if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) true else rowOfSquaresIsInvalid(Rows.BOTTOM, grid)
    }

    private fun rowOfSquaresIsInvalid(rows: Rows, grid: Array<IntArray>): Boolean {
        return when (rows) {
            Rows.TOP -> {
                if (squareIsInvalid(0, 0, grid)) return true
                if (squareIsInvalid(0, 3, grid)) true else squareIsInvalid(0, 6, grid)
            }

            Rows.MIDDLE -> {
                if (squareIsInvalid(3, 0, grid)) return true
                if (squareIsInvalid(3, 3, grid)) true else squareIsInvalid(3, 6, grid)
            }

            Rows.BOTTOM -> {
                if (squareIsInvalid(6, 0, grid)) return true
                if (squareIsInvalid(6, 3, grid)) true else squareIsInvalid(6, 6, grid)
            }
        }
    }

    private fun squareIsInvalid(xIndex: Int, yIndex: Int, grid: Array<IntArray>): Boolean {
        var xIndex = xIndex
        var yIndex = yIndex
        val yIndexEnd = yIndex + 3
        val xIndexEnd = xIndex + 3
        val square: MutableList<Int?> = ArrayList()

        while (yIndex < yIndexEnd) {
            while (xIndex < xIndexEnd) {
                square.add(grid[xIndex][yIndex])
                xIndex++
            }

            xIndex -= 3
            yIndex++
        }

        return collectionHasRepeats(square)
    }

    private fun collectionHasRepeats(square: List<Int?>): Boolean {
        for (index in 1..SudokuGame.GRID_BOUNDARY) {
            if (Collections.frequency(square, index) > 1) return true
        }

        return false
    }

    private fun tileAreNotFilled(grid: Array<IntArray>): Boolean {
        for (yIndex in 0..<SudokuGame.GRID_BOUNDARY) {
            for (xIndex in 0..<SudokuGame.GRID_BOUNDARY) {
                if (grid[xIndex][yIndex] == 0) return true
            }
        }

        return false
    }
}
