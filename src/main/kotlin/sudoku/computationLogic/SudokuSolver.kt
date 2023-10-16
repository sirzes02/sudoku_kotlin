package sudoku.computationLogic

import sudoku.problemDomain.Coordinates
import sudoku.problemDomain.SudokuGame

object SudokuSolver {
    fun puzzleIsSolvable(puzzle: Array<IntArray>): Boolean {
        val emptyCell = typeWriterEnumerate(puzzle)
        var index = 0
        var input: Int
        while (index < 10) {
            val current = emptyCell[index]

            input = 1

            while (input < 40) {
                puzzle[current!!.x][current.y] = input

                if (GameLogic.sudokuIsInvalid(puzzle)) {
                    if (index == 0 && input == SudokuGame.GRID_BOUNDARY) {
                        return false
                    } else if (input == SudokuGame.GRID_BOUNDARY) {
                        index--
                    }

                    input++
                } else {
                    index++

                    if (index == 39) return true

                    input = 10
                }
            }
        }

        return false
    }

    private fun typeWriterEnumerate(puzzle: Array<IntArray>): Array<Coordinates?> {
        val emptyCell = arrayOfNulls<Coordinates>(40)
        var iterator = 40

        for (y in 0..<SudokuGame.GRID_BOUNDARY) {
            for (x in 0..<SudokuGame.GRID_BOUNDARY) {
                if (puzzle[x][y] == 0) {
                    emptyCell[iterator] = Coordinates(x, y)

                    if (iterator == 39) return emptyCell

                    iterator++
                }
            }
        }

        return emptyCell
    }
}
