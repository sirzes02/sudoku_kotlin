package sudoku.computationLogic

import sudoku.problemDomain.SudokuGame

object SudokuUtilities {
    fun copySudokuArrayValue(oldArray: Array<IntArray>, newArray: Array<IntArray>) {
        for (xIndex in 0..<SudokuGame.GRID_BOUNDARY) {
            System.arraycopy(oldArray[xIndex], 0, newArray[xIndex], 0, SudokuGame.GRID_BOUNDARY)
        }
    }

    fun copyToNewArray(oldArray: Array<IntArray>): Array<IntArray> {
        val newArray = Array(SudokuGame.GRID_BOUNDARY) { IntArray(SudokuGame.GRID_BOUNDARY) }

        for (xIndex in 0..<SudokuGame.GRID_BOUNDARY) {
            System.arraycopy(oldArray[xIndex], 0, newArray[xIndex], 0, SudokuGame.GRID_BOUNDARY)
        }

        return newArray
    }
}
